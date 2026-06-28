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
import upc.service.taller.alumno.dto.PedidoDto;
import upc.service.taller.alumno.service.PedidoService;
import upc.service.taller.alumno.utils.RespuestaExcepcion;
import upc.service.taller.alumno.utils.RespuestaGenerica;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Operaciones CRUD para pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista pedidos", description = "Permite listar todos los pedidos registrados")
    public ResponseEntity<RespuestaGenerica<PedidoDto>> listar() {
        try {
            List<PedidoDto> listaPedidos = pedidoService.listar();
            return ResponseEntity.ok(RespuestaGenerica.<PedidoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .lista(listaPedidos)
                    .tamanioLista(listaPedidos.size())
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @GetMapping(value = "/{idPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene un pedido", description = "Permite obtener un pedido por su identificador")
    public ResponseEntity<RespuestaGenerica<PedidoDto>> obtenerPorId(@PathVariable Long idPedido) {
        try {
            PedidoDto pedido = pedidoService.obtenerPorId(idPedido);
            return ResponseEntity.ok(RespuestaGenerica.<PedidoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(pedido)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un pedido", description = "Permite registrar un nuevo pedido")
    public ResponseEntity<RespuestaGenerica<PedidoDto>> crear(@RequestBody PedidoDto pedido) {
        try {
            PedidoDto pedidoCreado = pedidoService.crear(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(RespuestaGenerica.<PedidoDto>builder()
                    .estadoCodigo(HttpStatus.CREATED.value())
                    .objeto(pedidoCreado)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PutMapping(value = "/{idPedido}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza un pedido", description = "Permite actualizar los datos de un pedido existente")
    public ResponseEntity<RespuestaGenerica<PedidoDto>> actualizar(
            @PathVariable Long idPedido,
            @RequestBody PedidoDto pedido
    ) {
        try {
            PedidoDto pedidoActualizado = pedidoService.actualizar(idPedido, pedido);
            return ResponseEntity.ok(RespuestaGenerica.<PedidoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(pedidoActualizado)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @DeleteMapping(value = "/{idPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Elimina un pedido", description = "Permite eliminar un pedido por su identificador")
    public ResponseEntity<RespuestaGenerica<PedidoDto>> eliminar(@PathVariable Long idPedido) {
        try {
            pedidoService.eliminar(idPedido);
            return ResponseEntity.ok(RespuestaGenerica.<PedidoDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .mensaje("Pedido eliminado correctamente")
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }
}
