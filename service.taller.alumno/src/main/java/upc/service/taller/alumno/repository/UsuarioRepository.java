package upc.service.taller.alumno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upc.service.taller.alumno.entity.UsuarioEntity;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    @Query("select u from UsuarioEntity u where u.UserName = :userName")
    Optional<UsuarioEntity> findByUserName(String userName);

    @Query("select count(u) > 0 from UsuarioEntity u where u.UserName = :userName")
    boolean existsByUserName(String userName);
}
