package upc.service.taller.alumno.service;

import upc.service.taller.alumno.dto.PersonaDto;

import java.util.List;

public interface PersonaService {

    List<PersonaDto> listar();

    PersonaDto obtenerPorId(Long idPersona);

    PersonaDto crear(PersonaDto persona);

    PersonaDto actualizar(Long idPersona, PersonaDto persona);

    void eliminar(Long idPersona);
}
