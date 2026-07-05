package upc.service.taller.alumno.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import upc.service.taller.alumno.entity.AuditoriaEntity;
import upc.service.taller.alumno.repository.AuditoriaRepository;
import upc.service.taller.alumno.service.AuditoriaService;

import java.util.Map;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;
    private final ObjectMapper objectMapper;

    public AuditoriaServiceImpl(AuditoriaRepository auditoriaRepository, ObjectMapper objectMapper) {
        this.auditoriaRepository = auditoriaRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void registrar(String tabla, String operacion, String registroId, Object dataNuevo, Object dataAnterior) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .tabla(tabla)
                .operacion(operacion)
                .registroId(registroId)
                .dataNuevo(convertirKeyValue(dataNuevo))
                .dataAnterior(convertirKeyValue(dataAnterior))
                .build();
        auditoriaRepository.save(auditoria);
    }

    private Map<String, Object> convertirKeyValue(Object object){
        if(object == null){
            return null;
        }

        return objectMapper.convertValue(object, new TypeReference<>() {
        });
    }
}
