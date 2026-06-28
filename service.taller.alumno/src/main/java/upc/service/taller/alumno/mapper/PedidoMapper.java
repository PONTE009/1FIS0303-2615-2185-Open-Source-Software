package upc.service.taller.alumno.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import upc.service.taller.alumno.dto.PedidoDto;
import upc.service.taller.alumno.entity.PedidoEntity;
import upc.service.taller.alumno.entity.UsuarioEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(source = "usuario.idUsuario", target = "idUsuario")
    PedidoDto toDto(PedidoEntity entity);

    @Mapping(target = "usuario", expression = "java(toUsuarioEntity(dto.getIdUsuario()))")
    @Mapping(target = "detallePedidos", ignore = true)
    PedidoEntity toEntity(PedidoDto dto);

    List<PedidoDto> toDtoList(List<PedidoEntity> entities);

    List<PedidoEntity> toEntityList(List<PedidoDto> dtos);

    default UsuarioEntity toUsuarioEntity(Long idUsuario) {
        if (idUsuario == null) {
            return null;
        }
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(idUsuario);
        return usuario;
    }
}
