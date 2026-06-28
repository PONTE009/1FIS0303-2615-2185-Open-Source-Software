package upc.service.taller.alumno.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import upc.service.taller.alumno.dto.ProductoProveedorDto;
import upc.service.taller.alumno.entity.ProductoEntity;
import upc.service.taller.alumno.entity.ProductoProveedorEntity;
import upc.service.taller.alumno.entity.ProveedoresEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoProveedorMapper {

    @Mapping(source = "proveedor.idProveedor", target = "idProveedor")
    @Mapping(source = "producto.idProducto", target = "idProducto")
    ProductoProveedorDto toDto(ProductoProveedorEntity entity);

    @Mapping(target = "proveedor", expression = "java(toProveedorEntity(dto.getIdProveedor()))")
    @Mapping(target = "producto", expression = "java(toProductoEntity(dto.getIdProducto()))")
    ProductoProveedorEntity toEntity(ProductoProveedorDto dto);

    List<ProductoProveedorDto> toDtoList(List<ProductoProveedorEntity> entities);

    List<ProductoProveedorEntity> toEntityList(List<ProductoProveedorDto> dtos);

    default ProveedoresEntity toProveedorEntity(Long idProveedor) {
        if (idProveedor == null) {
            return null;
        }
        ProveedoresEntity proveedor = new ProveedoresEntity();
        proveedor.setIdProveedor(idProveedor);
        return proveedor;
    }

    default ProductoEntity toProductoEntity(Long idProducto) {
        if (idProducto == null) {
            return null;
        }
        ProductoEntity producto = new ProductoEntity();
        producto.setIdProducto(idProducto);
        return producto;
    }
}
