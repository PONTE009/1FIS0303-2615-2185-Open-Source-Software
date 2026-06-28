package upc.service.taller.alumno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaDto {
    private String usuarioRegistro;
    private LocalDateTime fechaRegistro;
    private String usuarioModifica;
    private LocalDateTime fechaModifica;
    private Boolean activo;
}
