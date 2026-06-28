package upc.service.taller.alumno.service;

import upc.service.taller.alumno.dto.ProductoDto;

import java.util.List;

public interface ProductoService {

    List<ProductoDto> listar();

    ProductoDto obtenerPorId(Long idProducto);

    ProductoDto crear(ProductoDto producto);

    ProductoDto actualizar(Long idProducto, ProductoDto producto);

    void eliminar(Long idProducto);
}
