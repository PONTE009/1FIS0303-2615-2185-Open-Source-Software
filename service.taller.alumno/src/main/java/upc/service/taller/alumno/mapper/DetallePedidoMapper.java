package upc.service.taller.alumno.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import upc.service.taller.alumno.dto.DetallePedidoDto;
import upc.service.taller.alumno.entity.DetallePedidoEntity;
import upc.service.taller.alumno.entity.PedidoEntity;
import upc.service.taller.alumno.entity.ProductoEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetallePedidoMapper {

    @Mapping(source = "pedido.idPedido", target = "idPedido")
    @Mapping(source = "producto.idProducto", target = "idProducto")
    DetallePedidoDto toDto(DetallePedidoEntity entity);

    @Mapping(target = "pedido", expression = "java(toPedidoEntity(dto.getIdPedido()))")
    @Mapping(target = "producto", expression = "java(toProductoEntity(dto.getIdProducto()))")
    DetallePedidoEntity toEntity(DetallePedidoDto dto);

    List<DetallePedidoDto> toDtoList(List<DetallePedidoEntity> entities);

    List<DetallePedidoEntity> toEntityList(List<DetallePedidoDto> dtos);

    default PedidoEntity toPedidoEntity(Long idPedido) {
        if (idPedido == null) {
            return null;
        }
        PedidoEntity pedido = new PedidoEntity();
        pedido.setIdPedido(idPedido);
        return pedido;
    }

    default ProductoEntity toProductoEntity(Long idProducto) {
        if (idProducto == null) {
            return null;
        }
        ProductoEntity producto = new ProductoEntity();
        producto.setIdProducto(idProducto);
        return producto;
    }
}
