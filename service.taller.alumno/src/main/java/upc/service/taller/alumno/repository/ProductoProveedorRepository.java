package upc.service.taller.alumno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.service.taller.alumno.entity.ProductoProveedorEntity;

@Repository
public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedorEntity, Long> {
}
