package upc.service.taller.alumno.service;

import upc.service.taller.alumno.entity.ProductoEntity;

import java.util.List;

public interface ProductoService {

    List<ProductoEntity> listar();

    ProductoEntity obtenerPorId(Long idProducto);

    ProductoEntity crear(ProductoEntity producto);

    ProductoEntity actualizar(Long idProducto, ProductoEntity producto);

    void eliminar(Long idProducto);
}
