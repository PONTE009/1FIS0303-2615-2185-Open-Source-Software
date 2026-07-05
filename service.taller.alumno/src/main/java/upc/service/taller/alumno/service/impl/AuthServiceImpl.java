package upc.service.taller.alumno.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.dto.auth.AuthResponseDto;
import upc.service.taller.alumno.dto.auth.LoginRequestDto;
import upc.service.taller.alumno.dto.auth.RefreshTokenRequestDto;
import upc.service.taller.alumno.dto.auth.RefreshTokenResponseDto;
import upc.service.taller.alumno.dto.auth.RegistroUsuarioRequestDto;
import upc.service.taller.alumno.dto.auth.RegistroUsuarioResponseDto;
import upc.service.taller.alumno.entity.PersonaEntity;
import upc.service.taller.alumno.entity.UsuarioEntity;
import upc.service.taller.alumno.repository.PersonaRepository;
import upc.service.taller.alumno.repository.UsuarioRepository;
import upc.service.taller.alumno.security.JwtService;
import upc.service.taller.alumno.service.AuthService;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private static final String TOKEN_TYPE = "Bearer";

    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(
            PersonaRepository personaRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public RegistroUsuarioResponseDto registrar(RegistroUsuarioRequestDto request) {
        validarRegistro(request);

        LocalDateTime ahora = LocalDateTime.now();
        PersonaEntity persona = new PersonaEntity();
        persona.setNombres(request.getNombres());
        persona.setApellidos(request.getApellidos());
        persona.setCorreo(request.getCorreo());
        persona.setTelefono(request.getTelefono());
        persona.setDocumento(request.getDocumento());
        persona.setFechaRegistro(ahora);
        persona.setFechaModifica(null);
        persona.setActivo(true);
        PersonaEntity personaGuardada = personaRepository.save(persona);

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUserName(request.getUserName());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setPersona(personaGuardada);
        usuario.setFechaRegistro(ahora);
        usuario.setFechaModifica(null);
        usuario.setActivo(true);
        UsuarioEntity usuarioGuardado = usuarioRepository.save(usuario);

        return new RegistroUsuarioResponseDto(
                usuarioGuardado.getIdUsuario(),
                usuarioGuardado.getUserName(),
                personaGuardada.getIdPersona(),
                personaGuardada.getNombres(),
                personaGuardada.getApellidos(),
                personaGuardada.getCorreo(),
                personaGuardada.getDocumento()
        );
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        validarLogin(request);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas");
        }

        UsuarioEntity usuario = obtenerUsuarioPorUserName(request.getUserName());
        return new AuthResponseDto(
                jwtService.generarAccessToken(usuario),
                jwtService.generarRefreshToken(usuario),
                TOKEN_TYPE
        );
    }

    @Override
    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request) {
        if (request == null || !StringUtils.hasText(request.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el refreshToken");
        }

        String refreshToken = request.getRefreshToken();
        String userName;
        try {
            if (!jwtService.esRefreshToken(refreshToken)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El token enviado no es un refreshToken");
            }
            userName = jwtService.obtenerUserName(refreshToken);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "RefreshToken invalido");
        }

        UsuarioEntity usuario = obtenerUsuarioPorUserName(userName);
        if (!jwtService.tokenValido(refreshToken, usuario.getUserName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "RefreshToken invalido");
        }

        return new RefreshTokenResponseDto(
                jwtService.generarAccessToken(usuario),
                refreshToken,
                TOKEN_TYPE
        );
    }

    private void validarRegistro(RegistroUsuarioRequestDto request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar los datos de registro");
        }
        if (!StringUtils.hasText(request.getNombres())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar los nombres");
        }
        if (!StringUtils.hasText(request.getUserName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el userName");
        }
        if (!StringUtils.hasText(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el password");
        }
        if (usuarioRepository.existsByUserName(request.getUserName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El userName ya existe");
        }
        if (StringUtils.hasText(request.getCorreo()) && personaRepository.existsByCorreo(request.getCorreo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo ya existe");
        }
        if (StringUtils.hasText(request.getDocumento()) && personaRepository.existsByDocumento(request.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El documento ya existe");
        }
    }

    private void validarLogin(LoginRequestDto request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar las credenciales");
        }
        if (!StringUtils.hasText(request.getUserName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el userName");
        }
        if (!StringUtils.hasText(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el password");
        }
    }

    private UsuarioEntity obtenerUsuarioPorUserName(String userName) {
        return usuarioRepository.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }
}
