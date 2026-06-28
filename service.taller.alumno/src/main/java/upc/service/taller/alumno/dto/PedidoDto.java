package upc.service.taller.alumno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto extends AuditoriaDto {
    private Long idPedido;
    private LocalDateTime fechaPedido;
    private BigDecimal total;
    private Long idUsuario;
}
