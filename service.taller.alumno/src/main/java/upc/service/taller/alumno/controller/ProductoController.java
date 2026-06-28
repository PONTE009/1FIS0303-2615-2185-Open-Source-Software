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
import upc.service.taller.alumno.dto.ProductoDto;
import upc.service.taller.alumno.service.ProductoService;
import upc.service.taller.alumno.utils.RespuestaExcepcion;
import upc.service.taller.alumno.utils.RespuestaGenerica;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Operaciones CRUD para productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lista productos",
            description = "Permite listar todos los productos registrados"
    )
    public ResponseEntity<RespuestaGenerica<ProductoDto>> listar() {
        try {
            List<ProductoDto> listaProductosDto = productoService.listar();
            return ResponseEntity.ok(RespuestaGenerica.<ProductoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .lista(listaProductosDto)
                    .tamanioLista(listaProductosDto.size())
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @GetMapping(value = "/{idProducto}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Obtiene un producto",
            description = "Permite obtener un producto por su identificador"
    )
    public ResponseEntity<RespuestaGenerica<ProductoDto>> obtenerPorId(@PathVariable Long idProducto) {
        try {
            ProductoDto productoDto = productoService.obtenerPorId(idProducto);
            return ResponseEntity.ok(RespuestaGenerica.<ProductoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(productoDto)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Crea un producto",
            description = "Permite registrar un nuevo producto"
    )
    public ResponseEntity<RespuestaGenerica<ProductoDto>> crear(@RequestBody ProductoDto productoDto) {
        try {
            ProductoDto productoCreadoDto = productoService.crear(productoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(RespuestaGenerica.<ProductoDto>builder()
                    .estadoCodigo(HttpStatus.CREATED.value())
                    .objeto(productoCreadoDto)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PutMapping(value = "/{idProducto}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Actualiza un producto",
            description = "Permite actualizar los datos de un producto existente"
    )
    public ResponseEntity<RespuestaGenerica<ProductoDto>> actualizar(
            @PathVariable Long idProducto,
            @RequestBody ProductoDto productoDto
    ) {
        try {
            ProductoDto productoActualizadoDto = productoService.actualizar(idProducto, productoDto);
            return ResponseEntity.ok(RespuestaGenerica.<ProductoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(productoActualizadoDto)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @DeleteMapping(value = "/{idProducto}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Elimina un producto",
            description = "Permite eliminar un producto por su identificador"
    )
    public ResponseEntity<RespuestaGenerica<ProductoDto>> eliminar(@PathVariable Long idProducto) {
        try {
            productoService.eliminar(idProducto);
            return ResponseEntity.ok(RespuestaGenerica.<ProductoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .mensaje("Producto eliminado correctamente")
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }
}
