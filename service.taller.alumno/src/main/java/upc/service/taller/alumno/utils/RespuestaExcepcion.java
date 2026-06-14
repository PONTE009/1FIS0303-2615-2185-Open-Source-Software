package upc.service.taller.alumno.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public class RespuestaExcepcion {
    public static <T> ResponseEntity<RespuestaGenerica<T>> handleControllerException(Exception ex){
        if (ex instanceof ResponseStatusException responseStatusException) {
            HttpStatus status = HttpStatus.valueOf(responseStatusException.getStatusCode().value());
            return ResponseEntity.status(status)
                    .body(RespuestaGenerica.<T>builder()
                            .estadoCodigo(status.value())
                            .mensajeExcepcion(responseStatusException.getReason())
                            .error(true)
                            .build());
        }

        // Error 404
        if (ex instanceof java.util.NoSuchElementException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RespuestaGenerica.<T>builder()
                            .estadoCodigo(HttpStatus.NOT_FOUND.value())
                            .mensajeExcepcion(ex.getMessage())
                            .error(true)
                            .build()
                    );
        }
        // Error 400
        if (ex instanceof IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RespuestaGenerica.<T>builder()
                            .estadoCodigo(HttpStatus.BAD_REQUEST.value())
                            .mensajeExcepcion(ex.getMessage())
                            .error(true)
                            .build());
        }

        // Error 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RespuestaGenerica.<T>builder()
                        .estadoCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .mensaje(ApiConstante.STATUS_CODE_500)
                        .mensajeExcepcion(ex.getMessage())
                        .error(true)
                        .build());
    }
}
