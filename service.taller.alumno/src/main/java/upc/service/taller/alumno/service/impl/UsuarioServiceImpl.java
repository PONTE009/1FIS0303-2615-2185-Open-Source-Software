package upc.service.taller.alumno.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.dto.UsuarioDto;
import upc.service.taller.alumno.entity.PersonaEntity;
import upc.service.taller.alumno.entity.UsuarioEntity;
import upc.service.taller.alumno.mapper.UsuarioMapper;
import upc.service.taller.alumno.repository.PersonaRepository;
import upc.service.taller.alumno.repository.UsuarioRepository;
import upc.service.taller.alumno.service.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            PersonaRepository personaRepository,
            UsuarioMapper usuarioMapper
    ) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public List<UsuarioDto> listar() {
        return usuarioMapper.toDtoList(usuarioRepository.findAll());
    }

    @Override
    public UsuarioDto obtenerPorId(Long idUsuario) {
        return usuarioMapper.toDto(obtenerUsuarioPorId(idUsuario));
    }

    @Override
    public UsuarioDto crear(UsuarioDto usuarioDto) {
        UsuarioEntity usuario = usuarioMapper.toEntity(usuarioDto);
        usuario.setIdUsuario(null);
        usuario.setPersona(obtenerPersona(usuario));
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setFechaModifica(null);
        usuario.setActivo(valorPorDefecto(usuario.getActivo(), true));
        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDto actualizar(Long idUsuario, UsuarioDto usuarioDto) {
        UsuarioEntity usuario = usuarioMapper.toEntity(usuarioDto);
        UsuarioEntity usuarioActual = obtenerUsuarioPorId(idUsuario);
        usuarioActual.setUserName(usuario.getUserName());
        usuarioActual.setPassword(usuario.getPassword());
        usuarioActual.setPersona(obtenerPersona(usuario));
        usuarioActual.setActivo(usuario.getActivo());
        usuarioActual.setUsuarioModifica(usuario.getUsuarioModifica());
        usuarioActual.setFechaModifica(LocalDateTime.now());
        return usuarioMapper.toDto(usuarioRepository.save(usuarioActual));
    }

    @Override
    public void eliminar(Long idUsuario) {
        usuarioRepository.delete(obtenerUsuarioPorId(idUsuario));
    }

    private UsuarioEntity obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private PersonaEntity obtenerPersona(UsuarioEntity usuario) {
        if (usuario.getPersona() == null || usuario.getPersona().getIdPersona() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el id de la persona");
        }
        return personaRepository.findById(usuario.getPersona().getIdPersona())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));
    }

    private Boolean valorPorDefecto(Boolean valor, Boolean valorDefecto) {
        return valor != null ? valor : valorDefecto;
    }
}
