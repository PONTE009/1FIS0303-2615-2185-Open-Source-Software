package upc.service.taller.alumno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto extends AuditoriaDto {
    private Long idUsuario;
    private String userName;
    private String password;
    private Long idPersona;
}
