package upc.service.taller.alumno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upc.service.taller.alumno.dto.auth.AuthResponseDto;
import upc.service.taller.alumno.dto.auth.LoginRequestDto;
import upc.service.taller.alumno.dto.auth.RefreshTokenRequestDto;
import upc.service.taller.alumno.dto.auth.RefreshTokenResponseDto;
import upc.service.taller.alumno.dto.auth.RegistroUsuarioRequestDto;
import upc.service.taller.alumno.dto.auth.RegistroUsuarioResponseDto;
import upc.service.taller.alumno.service.AuthService;
import upc.service.taller.alumno.utils.RespuestaExcepcion;
import upc.service.taller.alumno.utils.RespuestaGenerica;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticacion", description = "Operaciones para registro, login y renovacion de tokens")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Registra un usuario", description = "Permite registrar persona y usuario sin generar tokens")
    public ResponseEntity<RespuestaGenerica<RegistroUsuarioResponseDto>> registrar(
            @RequestBody RegistroUsuarioRequestDto request
    ) {
        try {
            RegistroUsuarioResponseDto usuarioRegistrado = authService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(RespuestaGenerica.<RegistroUsuarioResponseDto>builder()
                    .estadoCodigo(HttpStatus.CREATED.value())
                    .mensaje("Usuario registrado correctamente")
                    .objeto(usuarioRegistrado)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Inicia sesion", description = "Permite iniciar sesion y generar accessToken y refreshToken")
    public ResponseEntity<RespuestaGenerica<AuthResponseDto>> login(@RequestBody LoginRequestDto request) {
        try {
            AuthResponseDto authResponse = authService.login(request);
            return ResponseEntity.ok(RespuestaGenerica.<AuthResponseDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(authResponse)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Renueva accessToken", description = "Permite generar un nuevo accessToken manteniendo el mismo refreshToken")
    public ResponseEntity<RespuestaGenerica<RefreshTokenResponseDto>> refreshToken(
            @RequestBody RefreshTokenRequestDto request
    ) {
        try {
            RefreshTokenResponseDto refreshResponse = authService.refreshToken(request);
            return ResponseEntity.ok(RespuestaGenerica.<RefreshTokenResponseDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(refreshResponse)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }
}
