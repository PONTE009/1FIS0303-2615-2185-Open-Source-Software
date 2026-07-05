package upc.service.taller.alumno.entity;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "auditoriastaller")
@TypeAlias("auditorias")
public class AuditoriaEntity {
    private String id;
    private String tabla;
    private String operacion;
    private String registroId;
    private Map<String,Object> dataNuevo;
    private Map<String,Object> dataAnterior;
    private LocalDateTime fechaRegistro;


}
