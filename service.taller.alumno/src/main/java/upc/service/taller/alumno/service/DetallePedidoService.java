package upc.service.taller.alumno.service;

import upc.service.taller.alumno.dto.DetallePedidoDto;

import java.util.List;

public interface DetallePedidoService {

    List<DetallePedidoDto> listar();

    DetallePedidoDto obtenerPorId(Long idDetallePedido);

    DetallePedidoDto crear(DetallePedidoDto detallePedido);

    DetallePedidoDto actualizar(Long idDetallePedido, DetallePedidoDto detallePedido);

    void eliminar(Long idDetallePedido);
}
