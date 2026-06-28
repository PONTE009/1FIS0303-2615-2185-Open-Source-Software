package upc.service.taller.alumno.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.dto.DetallePedidoDto;
import upc.service.taller.alumno.entity.DetallePedidoEntity;
import upc.service.taller.alumno.entity.PedidoEntity;
import upc.service.taller.alumno.entity.ProductoEntity;
import upc.service.taller.alumno.mapper.DetallePedidoMapper;
import upc.service.taller.alumno.repository.DetallePedidoRepository;
import upc.service.taller.alumno.repository.PedidoRepository;
import upc.service.taller.alumno.repository.ProductoRepository;
import upc.service.taller.alumno.service.DetallePedidoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final DetallePedidoMapper detallePedidoMapper;

    public DetallePedidoServiceImpl(
            DetallePedidoRepository detallePedidoRepository,
            PedidoRepository pedidoRepository,
            ProductoRepository productoRepository,
            DetallePedidoMapper detallePedidoMapper
    ) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.detallePedidoMapper = detallePedidoMapper;
    }

    @Override
    public List<DetallePedidoDto> listar() {
        return detallePedidoMapper.toDtoList(detallePedidoRepository.findAll());
    }

    @Override
    public DetallePedidoDto obtenerPorId(Long idDetallePedido) {
        return detallePedidoMapper.toDto(obtenerDetallePedidoPorId(idDetallePedido));
    }

    @Override
    public DetallePedidoDto crear(DetallePedidoDto detallePedidoDto) {
        DetallePedidoEntity detallePedido = detallePedidoMapper.toEntity(detallePedidoDto);
        detallePedido.setIdDetallePedido(null);
        detallePedido.setPedido(obtenerPedido(detallePedido));
        detallePedido.setProducto(obtenerProducto(detallePedido));
        detallePedido.setSubTotal(calcularSubTotal(detallePedido));
        detallePedido.setFechaRegistro(LocalDateTime.now());
        detallePedido.setFechaModifica(null);
        detallePedido.setActivo(valorPorDefecto(detallePedido.getActivo(), true));
        return detallePedidoMapper.toDto(detallePedidoRepository.save(detallePedido));
    }

    @Override
    public DetallePedidoDto actualizar(Long idDetallePedido, DetallePedidoDto detallePedidoDto) {
        DetallePedidoEntity detallePedido = detallePedidoMapper.toEntity(detallePedidoDto);
        DetallePedidoEntity detallePedidoActual = obtenerDetallePedidoPorId(idDetallePedido);
        detallePedidoActual.setCantidad(detallePedido.getCantidad());
        detallePedidoActual.setPrecioUnitario(detallePedido.getPrecioUnitario());
        detallePedidoActual.setSubTotal(calcularSubTotal(detallePedido));
        detallePedidoActual.setPedido(obtenerPedido(detallePedido));
        detallePedidoActual.setProducto(obtenerProducto(detallePedido));
        detallePedidoActual.setActivo(detallePedido.getActivo());
        detallePedidoActual.setUsuarioModifica(detallePedido.getUsuarioModifica());
        detallePedidoActual.setFechaModifica(LocalDateTime.now());
        return detallePedidoMapper.toDto(detallePedidoRepository.save(detallePedidoActual));
    }

    @Override
    public void eliminar(Long idDetallePedido) {
        detallePedidoRepository.delete(obtenerDetallePedidoPorId(idDetallePedido));
    }

    private DetallePedidoEntity obtenerDetallePedidoPorId(Long idDetallePedido) {
        return detallePedidoRepository.findById(idDetallePedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle de pedido no encontrado"));
    }

    private PedidoEntity obtenerPedido(DetallePedidoEntity detallePedido) {
        if (detallePedido.getPedido() == null || detallePedido.getPedido().getIdPedido() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el id del pedido");
        }
        return pedidoRepository.findById(detallePedido.getPedido().getIdPedido())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
    }

    private ProductoEntity obtenerProducto(DetallePedidoEntity detallePedido) {
        if (detallePedido.getProducto() == null || detallePedido.getProducto().getIdProducto() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el id del producto");
        }
        return productoRepository.findById(detallePedido.getProducto().getIdProducto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    private BigDecimal calcularSubTotal(DetallePedidoEntity detallePedido) {
        if (detallePedido.getSubTotal() != null) {
            return detallePedido.getSubTotal();
        }
        if (detallePedido.getCantidad() != null && detallePedido.getPrecioUnitario() != null) {
            return detallePedido.getPrecioUnitario().multiply(BigDecimal.valueOf(detallePedido.getCantidad()));
        }
        return null;
    }

    private Boolean valorPorDefecto(Boolean valor, Boolean valorDefecto) {
        return valor != null ? valor : valorDefecto;
    }
}
