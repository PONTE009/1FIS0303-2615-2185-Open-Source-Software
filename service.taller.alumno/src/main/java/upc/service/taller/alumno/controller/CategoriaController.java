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
import upc.service.taller.alumno.entity.CategoriaEntity;
import upc.service.taller.alumno.service.CategoriaService;
import upc.service.taller.alumno.utils.RespuestaExcepcion;
import upc.service.taller.alumno.utils.RespuestaGenerica;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorias", description = "Operaciones CRUD para categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lista categorias",
            description = "Permite listar todas las categorias registradas"
    )
    public ResponseEntity<RespuestaGenerica<CategoriaEntity>> listar() {
        try {
            List<CategoriaEntity> listaCategorias = categoriaService.listar();
            return ResponseEntity.ok(RespuestaGenerica.<CategoriaEntity>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .lista(listaCategorias)
                    .tamanioLista(listaCategorias.size())
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @GetMapping(value = "/{idCategoria}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Obtiene una categoria",
            description = "Permite obtener una categoria por su identificador"
    )
    public ResponseEntity<RespuestaGenerica<CategoriaEntity>> obtenerPorId(@PathVariable Long idCategoria) {
        try {
            CategoriaEntity categoria = categoriaService.obtenerPorId(idCategoria);
            return ResponseEntity.ok(RespuestaGenerica.<CategoriaEntity>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(categoria)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Crea una categoria",
            description = "Permite registrar una nueva categoria"
    )
    public ResponseEntity<RespuestaGenerica<CategoriaEntity>> crear(@RequestBody CategoriaEntity categoria) {
        try {
            CategoriaEntity categoriaCreada = categoriaService.crear(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(RespuestaGenerica.<CategoriaEntity>builder()
                    .estadoCodigo(HttpStatus.CREATED.value())
                    .objeto(categoriaCreada)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PutMapping(value = "/{idCategoria}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Actualiza una categoria",
            description = "Permite actualizar los datos de una categoria existente"
    )
    public ResponseEntity<RespuestaGenerica<CategoriaEntity>> actualizar(
            @PathVariable Long idCategoria,
            @RequestBody CategoriaEntity categoria
    ) {
        try {
            CategoriaEntity categoriaActualizada = categoriaService.actualizar(idCategoria, categoria);
            return ResponseEntity.ok(RespuestaGenerica.<CategoriaEntity>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(categoriaActualizada)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @DeleteMapping(value = "/{idCategoria}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Elimina una categoria",
            description = "Permite eliminar una categoria por su identificador"
    )
    public ResponseEntity<RespuestaGenerica<CategoriaEntity>> eliminar(@PathVariable Long idCategoria) {
        try {
            categoriaService.eliminar(idCategoria);
            return ResponseEntity.ok(RespuestaGenerica.<CategoriaEntity>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .mensaje("Categoria eliminada correctamente")
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }
}
