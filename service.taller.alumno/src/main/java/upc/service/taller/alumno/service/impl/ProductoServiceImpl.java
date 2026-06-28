package upc.service.taller.alumno.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.dto.ProductoDto;
import upc.service.taller.alumno.entity.CategoriaEntity;
import upc.service.taller.alumno.entity.ProductoEntity;
import upc.service.taller.alumno.mapper.ProductoMapper;
import upc.service.taller.alumno.repository.CategoriaRepository;
import upc.service.taller.alumno.repository.ProductoRepository;
import upc.service.taller.alumno.service.ProductoService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    public ProductoServiceImpl(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            ProductoMapper productoMapper
    ) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public List<ProductoDto> listar() {
        return productoMapper.toDtoList(productoRepository.findAll());
    }

    @Override
    public ProductoDto obtenerPorId(Long idProducto) {
        return productoMapper.toDto(obtenerProductoPorId(idProducto));
    }

    @Override
    public ProductoDto crear(ProductoDto productoDto) {
        ProductoEntity producto = productoMapper.toEntity(productoDto);
        LocalDateTime ahora = LocalDateTime.now();
        producto.setIdProducto(null);
        producto.setCategoria(obtenerCategoria(producto));
        producto.setFechaRegistro(ahora);
        producto.setFechaModifica(null);
        producto.setActivo(valorPorDefecto(producto.getActivo(), true));
        producto.setEstado(valorPorDefecto(producto.getEstado(), true));
        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public ProductoDto actualizar(Long idProducto, ProductoDto productoDto) {
        ProductoEntity producto = productoMapper.toEntity(productoDto);
        ProductoEntity productoActual = obtenerProductoPorId(idProducto);
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
        return productoMapper.toDto(productoRepository.save(productoActual));
    }

    @Override
    public void eliminar(Long idProducto) {
        ProductoEntity producto = obtenerProductoPorId(idProducto);
        productoRepository.delete(producto);
    }

    private ProductoEntity obtenerProductoPorId(Long idProducto) {
        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
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
