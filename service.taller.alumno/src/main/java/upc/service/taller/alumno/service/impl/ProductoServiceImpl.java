package upc.service.taller.alumno.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.entity.CategoriaEntity;
import upc.service.taller.alumno.entity.ProductoEntity;
import upc.service.taller.alumno.repository.CategoriaRepository;
import upc.service.taller.alumno.repository.ProductoRepository;
import upc.service.taller.alumno.service.ProductoService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<ProductoEntity> listar() {
        return productoRepository.findAll();
    }

    @Override
    public ProductoEntity obtenerPorId(Long idProducto) {
        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    @Override
    public ProductoEntity crear(ProductoEntity producto) {
        LocalDateTime ahora = LocalDateTime.now();
        producto.setIdProducto(null);
        producto.setCategoria(obtenerCategoria(producto));
        producto.setFechaRegistro(ahora);
        producto.setFechaModifica(null);
        producto.setActivo(valorPorDefecto(producto.getActivo(), true));
        producto.setEstado(valorPorDefecto(producto.getEstado(), true));
        return productoRepository.save(producto);
    }

    @Override
    public ProductoEntity actualizar(Long idProducto, ProductoEntity producto) {
        ProductoEntity productoActual = obtenerPorId(idProducto);
        productoActual.setCategoria(obtenerCategoria(producto));
        productoActual.setNombre(producto.getNombre());
        productoActual.setDescripcion(producto.getDescripcion());
        productoActual.setPrecio(producto.getPrecio());
        productoActual.setStock(producto.getStock());
        productoActual.setImagenUrl(producto.getImagenUrl());
        productoActual.setEstado(producto.getEstado());
        productoActual.setActivo(producto.getActivo());
        productoActual.setUsuarioModifica(producto.getUsuarioModifica());
        productoActual.setFechaModifica(LocalDateTime.now());
        return productoRepository.save(productoActual);
    }

    @Override
    public void eliminar(Long idProducto) {
        ProductoEntity producto = obtenerPorId(idProducto);
        productoRepository.delete(producto);
    }

    private CategoriaEntity obtenerCategoria(ProductoEntity producto) {
        if (producto.getCategoria() == null || producto.getCategoria().getIdCategoria() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el id de la categoria");
        }

        return categoriaRepository.findById(producto.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));
    }

    private Boolean valorPorDefecto(Boolean valor, Boolean valorDefecto) {
        return valor != null ? valor : valorDefecto;
    }
}
