package upc.service.taller.alumno.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import upc.service.taller.alumno.dto.PersonaDto;
import upc.service.taller.alumno.entity.PersonaEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

    PersonaDto toDto(PersonaEntity entity);

    @Mapping(target = "usuario", ignore = true)
    PersonaEntity toEntity(PersonaDto dto);

    List<PersonaDto> toDtoList(List<PersonaEntity> entities);

    List<PersonaEntity> toEntityList(List<PersonaDto> dtos);
}
