package upc.service.taller.alumno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.service.taller.alumno.entity.PersonaEntity;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {
    boolean existsByCorreo(String correo);

    boolean existsByDocumento(String documento);
}
