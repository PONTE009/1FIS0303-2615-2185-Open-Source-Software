package upc.service.taller.alumno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedoresDto {
    private Long idProveedor;
    private String razonSocial;
    private String ruc;
    private String correo;
    private String telefono;
    private String direccion;
}
