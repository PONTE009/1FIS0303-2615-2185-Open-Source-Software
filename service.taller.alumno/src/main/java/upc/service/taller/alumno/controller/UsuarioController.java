package upc.service.taller.alumno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upc.service.taller.alumno.dto.UsuarioDto;
import upc.service.taller.alumno.service.UsuarioService;
import upc.service.taller.alumno.utils.RespuestaExcepcion;
import upc.service.taller.alumno.utils.RespuestaGenerica;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones CRUD para usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista usuarios", description = "Permite listar todos los usuarios registrados")
    public ResponseEntity<RespuestaGenerica<UsuarioDto>> listar() {
        try {
            List<UsuarioDto> listaUsuarios = usuarioService.listar();
            return ResponseEntity.ok(RespuestaGenerica.<UsuarioDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .lista(listaUsuarios)
                    .tamanioLista(listaUsuarios.size())
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @GetMapping(value = "/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene un usuario", description = "Permite obtener un usuario por su identificador")
    public ResponseEntity<RespuestaGenerica<UsuarioDto>> obtenerPorId(@PathVariable Long idUsuario) {
        try {
            UsuarioDto usuario = usuarioService.obtenerPorId(idUsuario);
            return ResponseEntity.ok(RespuestaGenerica.<UsuarioDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(usuario)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un usuario", description = "Permite registrar un nuevo usuario")
    public ResponseEntity<RespuestaGenerica<UsuarioDto>> crear(@RequestBody UsuarioDto usuario) {
        try {
            UsuarioDto usuarioCreado = usuarioService.crear(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(RespuestaGenerica.<UsuarioDto>builder()
                    .estadoCodigo(HttpStatus.CREATED.value())
                    .objeto(usuarioCreado)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PutMapping(value = "/{idUsuario}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza un usuario", description = "Permite actualizar los datos de un usuario existente")
    public ResponseEntity<RespuestaGenerica<UsuarioDto>> actualizar(
            @PathVariable Long idUsuario,
            @RequestBody UsuarioDto usuario
    ) {
        try {
            UsuarioDto usuarioActualizado = usuarioService.actualizar(idUsuario, usuario);
            return ResponseEntity.ok(RespuestaGenerica.<UsuarioDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(usuarioActualizado)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @DeleteMapping(value = "/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Elimina un usuario", description = "Permite eliminar un usuario por su identificador")
    public ResponseEntity<RespuestaGenerica<UsuarioDto>> eliminar(@PathVariable Long idUsuario) {
        try {
            usuarioService.eliminar(idUsuario);
            return ResponseEntity.ok(RespuestaGenerica.<UsuarioDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .mensaje("Usuario eliminado correctamente")
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }
}
