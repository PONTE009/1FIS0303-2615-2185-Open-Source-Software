package upc.service.taller.alumno.service;

import upc.service.taller.alumno.dto.PedidoDto;

import java.util.List;

public interface PedidoService {

    List<PedidoDto> listar();

    PedidoDto obtenerPorId(Long idPedido);

    PedidoDto crear(PedidoDto pedido);

    PedidoDto actualizar(Long idPedido, PedidoDto pedido);

    void eliminar(Long idPedido);
}
