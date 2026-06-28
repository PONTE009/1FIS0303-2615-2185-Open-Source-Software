package upc.service.taller.alumno.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import upc.service.taller.alumno.dto.UsuarioDto;
import upc.service.taller.alumno.entity.PersonaEntity;
import upc.service.taller.alumno.entity.UsuarioEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "persona.idPersona", target = "idPersona")
    UsuarioDto toDto(UsuarioEntity entity);

    @Mapping(target = "persona", expression = "java(toPersonaEntity(dto.getIdPersona()))")
    @Mapping(target = "pedidos", ignore = true)
    UsuarioEntity toEntity(UsuarioDto dto);

    List<UsuarioDto> toDtoList(List<UsuarioEntity> entities);

    List<UsuarioEntity> toEntityList(List<UsuarioDto> dtos);

    default PersonaEntity toPersonaEntity(Long idPersona) {
        if (idPersona == null) {
            return null;
        }
        PersonaEntity persona = new PersonaEntity();
        persona.setIdPersona(idPersona);
        return persona;
    }
}
