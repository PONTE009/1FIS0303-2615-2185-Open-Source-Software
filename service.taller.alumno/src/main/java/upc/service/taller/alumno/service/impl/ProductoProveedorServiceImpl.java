package upc.service.taller.alumno.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.dto.ProductoProveedorDto;
import upc.service.taller.alumno.entity.ProductoEntity;
import upc.service.taller.alumno.entity.ProductoProveedorEntity;
import upc.service.taller.alumno.entity.ProveedoresEntity;
import upc.service.taller.alumno.mapper.ProductoProveedorMapper;
import upc.service.taller.alumno.repository.ProductoProveedorRepository;
import upc.service.taller.alumno.repository.ProductoRepository;
import upc.service.taller.alumno.repository.ProveedoresRepository;
import upc.service.taller.alumno.service.ProductoProveedorService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductoProveedorServiceImpl implements ProductoProveedorService {

    private final ProductoProveedorRepository productoProveedorRepository;
    private final ProveedoresRepository proveedoresRepository;
    private final ProductoRepository productoRepository;
    private final ProductoProveedorMapper productoProveedorMapper;

    public ProductoProveedorServiceImpl(
            ProductoProveedorRepository productoProveedorRepository,
            ProveedoresRepository proveedoresRepository,
            ProductoRepository productoRepository,
            ProductoProveedorMapper productoProveedorMapper
    ) {
        this.productoProveedorRepository = productoProveedorRepository;
        this.proveedoresRepository = proveedoresRepository;
        this.productoRepository = productoRepository;
        this.productoProveedorMapper = productoProveedorMapper;
    }

    @Override
    public List<ProductoProveedorDto> listar() {
        return productoProveedorMapper.toDtoList(productoProveedorRepository.findAll());
    }

    @Override
    public ProductoProveedorDto obtenerPorId(Long idProductoProveedor) {
        return productoProveedorMapper.toDto(obtenerProductoProveedorPorId(idProductoProveedor));
    }

    @Override
    public ProductoProveedorDto crear(ProductoProveedorDto productoProveedorDto) {
        ProductoProveedorEntity productoProveedor = productoProveedorMapper.toEntity(productoProveedorDto);
        productoProveedor.setIdProductoProveedor(null);
        productoProveedor.setProveedor(obtenerProveedor(productoProveedor));
        productoProveedor.setProducto(obtenerProducto(productoProveedor));
        productoProveedor.setFechaRegistro(LocalDateTime.now());
        productoProveedor.setFechaModifica(null);
        productoProveedor.setActivo(valorPorDefecto(productoProveedor.getActivo(), true));
        return productoProveedorMapper.toDto(productoProveedorRepository.save(productoProveedor));
    }

    @Override
    public ProductoProveedorDto actualizar(Long idProductoProveedor, ProductoProveedorDto productoProveedorDto) {
        ProductoProveedorEntity productoProveedor = productoProveedorMapper.toEntity(productoProveedorDto);
        ProductoProveedorEntity productoProveedorActual = obtenerProductoProveedorPorId(idProductoProveedor);
        productoProveedorActual.setPrecioCompra(productoProveedor.getPrecioCompra());
        productoProveedorActual.setProveedor(obtenerProveedor(productoProveedor));
        productoProveedorActual.setProducto(obtenerProducto(productoProveedor));
        productoProveedorActual.setActivo(productoProveedor.getActivo());
        productoProveedorActual.setUsuarioModifica(productoProveedor.getUsuarioModifica());
        productoProveedorActual.setFechaModifica(LocalDateTime.now());
        return productoProveedorMapper.toDto(productoProveedorRepository.save(productoProveedorActual));
    }

    @Override
    public void eliminar(Long idProductoProveedor) {
        productoProveedorRepository.delete(obtenerProductoProveedorPorId(idProductoProveedor));
    }

    private ProductoProveedorEntity obtenerProductoProveedorPorId(Long idProductoProveedor) {
        return productoProveedorRepository.findById(idProductoProveedor)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto proveedor no encontrado"));
    }

    private ProveedoresEntity obtenerProveedor(ProductoProveedorEntity productoProveedor) {
        if (productoProveedor.getProveedor() == null || productoProveedor.getProveedor().getIdProveedor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el id del proveedor");
        }
        return proveedoresRepository.findById(productoProveedor.getProveedor().getIdProveedor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado"));
    }

    private ProductoEntity obtenerProducto(ProductoProveedorEntity productoProveedor) {
        if (productoProveedor.getProducto() == null || productoProveedor.getProducto().getIdProducto() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el id del producto");
        }
        return productoRepository.findById(productoProveedor.getProducto().getIdProducto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    private Boolean valorPorDefecto(Boolean valor, Boolean valorDefecto) {
        return valor != null ? valor : valorDefecto;
    }
}
