package upc.service.taller.alumno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoProveedorDto extends AuditoriaDto {
    private Long idProductoProveedor;
    private BigDecimal precioCompra;
    private Long idProveedor;
    private Long idProducto;
}
