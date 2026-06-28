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
import upc.service.taller.alumno.dto.DetallePedidoDto;
import upc.service.taller.alumno.service.DetallePedidoService;
import upc.service.taller.alumno.utils.RespuestaExcepcion;
import upc.service.taller.alumno.utils.RespuestaGenerica;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detalle-pedidos")
@Tag(name = "Detalle pedidos", description = "Operaciones CRUD para detalle de pedidos")
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista detalles de pedido", description = "Permite listar todos los detalles de pedido registrados")
    public ResponseEntity<RespuestaGenerica<DetallePedidoDto>> listar() {
        try {
            List<DetallePedidoDto> listaDetallePedidos = detallePedidoService.listar();
            return ResponseEntity.ok(RespuestaGenerica.<DetallePedidoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .lista(listaDetallePedidos)
                    .tamanioLista(listaDetallePedidos.size())
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @GetMapping(value = "/{idDetallePedido}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene un detalle de pedido", description = "Permite obtener un detalle de pedido por su identificador")
    public ResponseEntity<RespuestaGenerica<DetallePedidoDto>> obtenerPorId(@PathVariable Long idDetallePedido) {
        try {
            DetallePedidoDto detallePedido = detallePedidoService.obtenerPorId(idDetallePedido);
            return ResponseEntity.ok(RespuestaGenerica.<DetallePedidoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(detallePedido)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un detalle de pedido", description = "Permite registrar un nuevo detalle de pedido")
    public ResponseEntity<RespuestaGenerica<DetallePedidoDto>> crear(@RequestBody DetallePedidoDto detallePedido) {
        try {
            DetallePedidoDto detallePedidoCreado = detallePedidoService.crear(detallePedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(RespuestaGenerica.<DetallePedidoDto>builder()
                    .estadoCodigo(HttpStatus.CREATED.value())
                    .objeto(detallePedidoCreado)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PutMapping(value = "/{idDetallePedido}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza un detalle de pedido", description = "Permite actualizar los datos de un detalle de pedido existente")
    public ResponseEntity<RespuestaGenerica<DetallePedidoDto>> actualizar(
            @PathVariable Long idDetallePedido,
            @RequestBody DetallePedidoDto detallePedido
    ) {
        try {
            DetallePedidoDto detallePedidoActualizado = detallePedidoService.actualizar(idDetallePedido, detallePedido);
            return ResponseEntity.ok(RespuestaGenerica.<DetallePedidoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(detallePedidoActualizado)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @DeleteMapping(value = "/{idDetallePedido}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Elimina un detalle de pedido", description = "Permite eliminar un detalle de pedido por su identificador")
    public ResponseEntity<RespuestaGenerica<DetallePedidoDto>> eliminar(@PathVariable Long idDetallePedido) {
        try {
            detallePedidoService.eliminar(idDetallePedido);
            return ResponseEntity.ok(RespuestaGenerica.<DetallePedidoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .mensaje("Detalle de pedido eliminado correctamente")
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }
}
