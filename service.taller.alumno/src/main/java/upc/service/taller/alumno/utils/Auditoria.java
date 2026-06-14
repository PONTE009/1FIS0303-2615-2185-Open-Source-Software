package upc.service.taller.alumno.utils;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public class Auditoria {
    @Column(name = "usuario_registro", length = 8)
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    @Column(name = "usuario_modifica", length = 8)
    private String usuarioModifica;
    @Column(name = "fecha_modifica")
    private LocalDateTime fechaModifica;
    private Boolean activo;
 }
