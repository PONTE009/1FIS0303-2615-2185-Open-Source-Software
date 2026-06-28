package upc.service.taller.alumno.service;

import upc.service.taller.alumno.dto.ProductoProveedorDto;

import java.util.List;

public interface ProductoProveedorService {

    List<ProductoProveedorDto> listar();

    ProductoProveedorDto obtenerPorId(Long idProductoProveedor);

    ProductoProveedorDto crear(ProductoProveedorDto productoProveedor);

    ProductoProveedorDto actualizar(Long idProductoProveedor, ProductoProveedorDto productoProveedor);

    void eliminar(Long idProductoProveedor);
}
