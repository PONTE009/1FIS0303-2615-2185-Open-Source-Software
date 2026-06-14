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
import upc.service.taller.alumno.entity.ProductoEntity;
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
    public ResponseEntity<RespuestaGenerica<ProductoEntity>> listar() {
        try {
            List<ProductoEntity> listaProductos = productoService.listar();
            return ResponseEntity.ok(RespuestaGenerica.<ProductoEntity>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .lista(listaProductos)
                    .tamanioLista(listaProductos.size())
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
    public ResponseEntity<RespuestaGenerica<ProductoEntity>> obtenerPorId(@PathVariable Long idProducto) {
        try {
            ProductoEntity producto = productoService.obtenerPorId(idProducto);
            return ResponseEntity.ok(RespuestaGenerica.<ProductoEntity>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(producto)
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
    public ResponseEntity<RespuestaGenerica<ProductoEntity>> crear(@RequestBody ProductoEntity producto) {
        try {
            ProductoEntity productoCreado = productoService.crear(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(RespuestaGenerica.<ProductoEntity>builder()
                    .estadoCodigo(HttpStatus.CREATED.value())
                    .objeto(productoCreado)
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
    public ResponseEntity<RespuestaGenerica<ProductoEntity>> actualizar(
            @PathVariable Long idProducto,
            @RequestBody ProductoEntity producto
    ) {
        try {
            ProductoEntity productoActualizado = productoService.actualizar(idProducto, producto);
            return ResponseEntity.ok(RespuestaGenerica.<ProductoEntity>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(productoActualizado)
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
    public ResponseEntity<RespuestaGenerica<ProductoEntity>> eliminar(@PathVariable Long idProducto) {
        try {
            productoService.eliminar(idProducto);
            return ResponseEntity.ok(RespuestaGenerica.<ProductoEntity>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .mensaje("Producto eliminado correctamente")
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }
}
