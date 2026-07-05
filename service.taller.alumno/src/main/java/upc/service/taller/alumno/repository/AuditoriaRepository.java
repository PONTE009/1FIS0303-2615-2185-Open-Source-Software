package upc.service.taller.alumno.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import upc.service.taller.alumno.entity.AuditoriaEntity;

public interface AuditoriaRepository extends MongoRepository<AuditoriaEntity, String> {
}
