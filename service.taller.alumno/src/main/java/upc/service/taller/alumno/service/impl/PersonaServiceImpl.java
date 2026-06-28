package upc.service.taller.alumno.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.dto.PersonaDto;
import upc.service.taller.alumno.entity.PersonaEntity;
import upc.service.taller.alumno.mapper.PersonaMapper;
import upc.service.taller.alumno.repository.PersonaRepository;
import upc.service.taller.alumno.service.PersonaService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;

    public PersonaServiceImpl(PersonaRepository personaRepository, PersonaMapper personaMapper) {
        this.personaRepository = personaRepository;
        this.personaMapper = personaMapper;
    }

    @Override
    public List<PersonaDto> listar() {
        return personaMapper.toDtoList(personaRepository.findAll());
    }

    @Override
    public PersonaDto obtenerPorId(Long idPersona) {
        return personaMapper.toDto(obtenerPersonaPorId(idPersona));
    }

    @Override
    public PersonaDto crear(PersonaDto personaDto) {
        PersonaEntity persona = personaMapper.toEntity(personaDto);
        persona.setIdPersona(null);
        persona.setFechaRegistro(LocalDateTime.now());
        persona.setFechaModifica(null);
        persona.setActivo(valorPorDefecto(persona.getActivo(), true));
        return personaMapper.toDto(personaRepository.save(persona));
    }

    @Override
    public PersonaDto actualizar(Long idPersona, PersonaDto personaDto) {
        PersonaEntity persona = personaMapper.toEntity(personaDto);
        PersonaEntity personaActual = obtenerPersonaPorId(idPersona);
        personaActual.setNombres(persona.getNombres());
        personaActual.setApellidos(persona.getApellidos());
        personaActual.setCorreo(persona.getCorreo());
        personaActual.setTelefono(persona.getTelefono());
        personaActual.setDocumento(persona.getDocumento());
        personaActual.setActivo(persona.getActivo());
        personaActual.setUsuarioModifica(persona.getUsuarioModifica());
        personaActual.setFechaModifica(LocalDateTime.now());
        return personaMapper.toDto(personaRepository.save(personaActual));
    }

    @Override
    public void eliminar(Long idPersona) {
        personaRepository.delete(obtenerPersonaPorId(idPersona));
    }

    private PersonaEntity obtenerPersonaPorId(Long idPersona) {
        return personaRepository.findById(idPersona)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));
    }

    private Boolean valorPorDefecto(Boolean valor, Boolean valorDefecto) {
        return valor != null ? valor : valorDefecto;
    }
}
