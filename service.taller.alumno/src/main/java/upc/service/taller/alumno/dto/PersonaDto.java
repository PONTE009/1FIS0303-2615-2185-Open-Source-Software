package upc.service.taller.alumno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDto extends AuditoriaDto {
    private Long idPersona;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String documento;
}
