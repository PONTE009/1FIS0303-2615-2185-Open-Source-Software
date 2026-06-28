package upc.service.taller.alumno.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import upc.service.taller.alumno.dto.ProductoDto;
import upc.service.taller.alumno.entity.CategoriaEntity;
import upc.service.taller.alumno.entity.ProductoEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    @Mapping(source = "categoria.idCategoria", target = "idCategoria")
    ProductoDto toDto(ProductoEntity entity);

    @Mapping(target = "categoria", expression = "java(toCategoriaEntity(dto.getIdCategoria()))")
    @Mapping(target = "productosProveedores", ignore = true)
    ProductoEntity toEntity(ProductoDto dto);

    List<ProductoDto> toDtoList(List<ProductoEntity> entities);

    List<ProductoEntity> toEntityList(List<ProductoDto> dtos);

    default CategoriaEntity toCategoriaEntity(Long idCategoria) {
        if (idCategoria == null) {
            return null;
        }
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setIdCategoria(idCategoria);
        return categoria;
    }
}
