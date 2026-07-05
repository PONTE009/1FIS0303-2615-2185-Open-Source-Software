package upc.service.taller.alumno.service;

import java.util.Map;

public interface AuditoriaService {
    void registrar(String tabla, String operacion, String registroId, Object dataNuevo, Object dataAnterior );
}
