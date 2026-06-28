package upc.service.taller.alumno.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import upc.service.taller.alumno.dto.CategoriaDto;
import upc.service.taller.alumno.entity.CategoriaEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaDto toDto(CategoriaEntity entity);

    @Mapping(target = "productos", ignore = true)
    CategoriaEntity toEntity(CategoriaDto dto);

    List<CategoriaDto> toDtoList(List<CategoriaEntity> entities);

    List<CategoriaEntity> toEntityList(List<CategoriaDto> dtos);
}
