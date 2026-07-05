package upc.service.taller.alumno.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioRequestDto {
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String documento;
    private String userName;
    private String password;
}
