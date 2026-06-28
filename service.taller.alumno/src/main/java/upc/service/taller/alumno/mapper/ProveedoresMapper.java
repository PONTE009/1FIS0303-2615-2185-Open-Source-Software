package upc.service.taller.alumno.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import upc.service.taller.alumno.dto.ProveedoresDto;
import upc.service.taller.alumno.entity.ProveedoresEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProveedoresMapper {

    ProveedoresDto toDto(ProveedoresEntity entity);

    @Mapping(target = "productosProveedores", ignore = true)
    ProveedoresEntity toEntity(ProveedoresDto dto);

    List<ProveedoresDto> toDtoList(List<ProveedoresEntity> entities);

    List<ProveedoresEntity> toEntityList(List<ProveedoresDto> dtos);
}
