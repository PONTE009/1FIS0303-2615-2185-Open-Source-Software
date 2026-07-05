package upc.service.taller.alumno.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioResponseDto {
    private Long idUsuario;
    private String userName;
    private Long idPersona;
    private String nombres;
    private String apellidos;
    private String correo;
    private String documento;
}
