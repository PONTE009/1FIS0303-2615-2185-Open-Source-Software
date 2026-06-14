package upc.service.taller.alumno.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaGenerica<T> {
    private int estadoCodigo;
    @Builder.Default
    private boolean error = false;
    @Builder.Default
    private String mensaje = "";
    @Builder.Default
    private String mensajeExcepcion = "";
    @Builder.Default
    private List<T> lista = new ArrayList<>();
    @Builder.Default
    private int tamanioLista = 0;
    private T objeto;
}

