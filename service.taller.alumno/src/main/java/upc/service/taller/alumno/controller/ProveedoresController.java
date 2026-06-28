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
import upc.service.taller.alumno.dto.ProveedoresDto;
import upc.service.taller.alumno.service.ProveedoresService;
import upc.service.taller.alumno.utils.RespuestaExcepcion;
import upc.service.taller.alumno.utils.RespuestaGenerica;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proveedores")
@Tag(name = "Proveedores", description = "Operaciones CRUD para proveedores")
public class ProveedoresController {

    private final ProveedoresService proveedoresService;

    public ProveedoresController(ProveedoresService proveedoresService) {
        this.proveedoresService = proveedoresService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista proveedores", description = "Permite listar todos los proveedores registrados")
    public ResponseEntity<RespuestaGenerica<ProveedoresDto>> listar() {
        try {
            List<ProveedoresDto> listaProveedores = proveedoresService.listar();
            return ResponseEntity.ok(RespuestaGenerica.<ProveedoresDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .lista(listaProveedores)
                    .tamanioLista(listaProveedores.size())
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @GetMapping(value = "/{idProveedor}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene un proveedor", description = "Permite obtener un proveedor por su identificador")
    public ResponseEntity<RespuestaGenerica<ProveedoresDto>> obtenerPorId(@PathVariable Long idProveedor) {
        try {
            ProveedoresDto proveedor = proveedoresService.obtenerPorId(idProveedor);
            return ResponseEntity.ok(RespuestaGenerica.<ProveedoresDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(proveedor)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un proveedor", description = "Permite registrar un nuevo proveedor")
    public ResponseEntity<RespuestaGenerica<ProveedoresDto>> crear(@RequestBody ProveedoresDto proveedor) {
        try {
            ProveedoresDto proveedorCreado = proveedoresService.crear(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(RespuestaGenerica.<ProveedoresDto>builder()
                    .estadoCodigo(HttpStatus.CREATED.value())
                    .objeto(proveedorCreado)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PutMapping(value = "/{idProveedor}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza un proveedor", description = "Permite actualizar los datos de un proveedor existente")
    public ResponseEntity<RespuestaGenerica<ProveedoresDto>> actualizar(
            @PathVariable Long idProveedor,
            @RequestBody ProveedoresDto proveedor
    ) {
        try {
            ProveedoresDto proveedorActualizado = proveedoresService.actualizar(idProveedor, proveedor);
            return ResponseEntity.ok(RespuestaGenerica.<ProveedoresDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(proveedorActualizado)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @DeleteMapping(value = "/{idProveedor}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Elimina un proveedor", description = "Permite eliminar un proveedor por su identificador")
    public ResponseEntity<RespuestaGenerica<ProveedoresDto>> eliminar(@PathVariable Long idProveedor) {
        try {
            proveedoresService.eliminar(idProveedor);
            return ResponseEntity.ok(RespuestaGenerica.<ProveedoresDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .mensaje("Proveedor eliminado correctamente")
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }
}
