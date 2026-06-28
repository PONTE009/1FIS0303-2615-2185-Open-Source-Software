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
import upc.service.taller.alumno.dto.ProductoProveedorDto;
import upc.service.taller.alumno.service.ProductoProveedorService;
import upc.service.taller.alumno.utils.RespuestaExcepcion;
import upc.service.taller.alumno.utils.RespuestaGenerica;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos-proveedores")
@Tag(name = "Productos proveedores", description = "Operaciones CRUD para productos proveedores")
public class ProductoProveedorController {

    private final ProductoProveedorService productoProveedorService;

    public ProductoProveedorController(ProductoProveedorService productoProveedorService) {
        this.productoProveedorService = productoProveedorService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista productos proveedores", description = "Permite listar todos los productos proveedores registrados")
    public ResponseEntity<RespuestaGenerica<ProductoProveedorDto>> listar() {
        try {
            List<ProductoProveedorDto> listaProductosProveedores = productoProveedorService.listar();
            return ResponseEntity.ok(RespuestaGenerica.<ProductoProveedorDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .lista(listaProductosProveedores)
                    .tamanioLista(listaProductosProveedores.size())
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @GetMapping(value = "/{idProductoProveedor}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene un producto proveedor", description = "Permite obtener un producto proveedor por su identificador")
    public ResponseEntity<RespuestaGenerica<ProductoProveedorDto>> obtenerPorId(@PathVariable Long idProductoProveedor) {
        try {
            ProductoProveedorDto productoProveedor = productoProveedorService.obtenerPorId(idProductoProveedor);
            return ResponseEntity.ok(RespuestaGenerica.<ProductoProveedorDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(productoProveedor)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un producto proveedor", description = "Permite registrar un nuevo producto proveedor")
    public ResponseEntity<RespuestaGenerica<ProductoProveedorDto>> crear(@RequestBody ProductoProveedorDto productoProveedor) {
        try {
            ProductoProveedorDto productoProveedorCreado = productoProveedorService.crear(productoProveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(RespuestaGenerica.<ProductoProveedorDto>builder()
                    .estadoCodigo(HttpStatus.CREATED.value())
                    .objeto(productoProveedorCreado)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PutMapping(value = "/{idProductoProveedor}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza un producto proveedor", description = "Permite actualizar los datos de un producto proveedor existente")
    public ResponseEntity<RespuestaGenerica<ProductoProveedorDto>> actualizar(
            @PathVariable Long idProductoProveedor,
            @RequestBody ProductoProveedorDto productoProveedor
    ) {
        try {
            ProductoProveedorDto productoProveedorActualizado = productoProveedorService.actualizar(idProductoProveedor, productoProveedor);
            return ResponseEntity.ok(RespuestaGenerica.<ProductoProveedorDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(productoProveedorActualizado)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @DeleteMapping(value = "/{idProductoProveedor}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Elimina un producto proveedor", description = "Permite eliminar un producto proveedor por su identificador")
    public ResponseEntity<RespuestaGenerica<ProductoProveedorDto>> eliminar(@PathVariable Long idProductoProveedor) {
        try {
            productoProveedorService.eliminar(idProductoProveedor);
            return ResponseEntity.ok(RespuestaGenerica.<ProductoProveedorDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .mensaje("Producto proveedor eliminado correctamente")
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }
}
