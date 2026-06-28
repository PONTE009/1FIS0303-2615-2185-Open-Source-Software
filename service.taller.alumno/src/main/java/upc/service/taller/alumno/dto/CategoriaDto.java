package upc.service.taller.alumno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDto extends AuditoriaDto {
    private Long idCategoria;
    private String nombre;
    private String descripcion;
    private Boolean estado;
}
