package upc.service.taller.alumno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upc.service.taller.alumno.dto.PersonaDto;
import upc.service.taller.alumno.service.PersonaService;
import upc.service.taller.alumno.utils.RespuestaExcepcion;
import upc.service.taller.alumno.utils.RespuestaGenerica;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personas")
@Tag(name = "Personas", description = "Operaciones CRUD para personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista personas", description = "Permite listar todas las personas registradas")
    public ResponseEntity<RespuestaGenerica<PersonaDto>> listar() {
        try {
            List<PersonaDto> listaPersonas = personaService.listar();
            return ResponseEntity.ok(RespuestaGenerica.<PersonaDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .lista(listaPersonas)
                    .tamanioLista(listaPersonas.size())
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @GetMapping(value = "/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene una persona", description = "Permite obtener una persona por su identificador")
    public ResponseEntity<RespuestaGenerica<PersonaDto>> obtenerPorId(@PathVariable Long idPersona) {
        try {
            PersonaDto persona = personaService.obtenerPorId(idPersona);
            return ResponseEntity.ok(RespuestaGenerica.<PersonaDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(persona)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea una persona", description = "Permite registrar una nueva persona")
    public ResponseEntity<RespuestaGenerica<PersonaDto>> crear(@RequestBody PersonaDto persona) {
        try {
            PersonaDto personaCreada = personaService.crear(persona);
            return ResponseEntity.status(HttpStatus.CREATED).body(RespuestaGenerica.<PersonaDto>builder()
                    .estadoCodigo(HttpStatus.CREATED.value())
                    .objeto(personaCreada)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @PutMapping(value = "/{idPersona}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza una persona", description = "Permite actualizar los datos de una persona existente")
    public ResponseEntity<RespuestaGenerica<PersonaDto>> actualizar(
            @PathVariable Long idPersona,
            @RequestBody PersonaDto persona
    ) {
        try {
            PersonaDto personaActualizada = personaService.actualizar(idPersona, persona);
            return ResponseEntity.ok(RespuestaGenerica.<PersonaDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .objeto(personaActualizada)
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }

    @DeleteMapping(value = "/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Elimina una persona", description = "Permite eliminar una persona por su identificador")
    public ResponseEntity<RespuestaGenerica<PersonaDto>> eliminar(@PathVariable Long idPersona) {
        try {
            personaService.eliminar(idPersona);
            return ResponseEntity.ok(RespuestaGenerica.<PersonaDto>builder()
                    .estadoCodigo(HttpStatus.OK.value())
                    .mensaje("Persona eliminada correctamente")
                    .build());
        } catch (Exception e) {
            return RespuestaExcepcion.handleControllerException(e);
        }
    }
}
