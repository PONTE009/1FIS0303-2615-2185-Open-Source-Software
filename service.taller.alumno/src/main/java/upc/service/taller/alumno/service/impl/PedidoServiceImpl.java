package upc.service.taller.alumno.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.dto.PedidoDto;
import upc.service.taller.alumno.entity.PedidoEntity;
import upc.service.taller.alumno.entity.UsuarioEntity;
import upc.service.taller.alumno.mapper.PedidoMapper;
import upc.service.taller.alumno.repository.PedidoRepository;
import upc.service.taller.alumno.repository.UsuarioRepository;
import upc.service.taller.alumno.service.PedidoService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoServiceImpl(
            PedidoRepository pedidoRepository,
            UsuarioRepository usuarioRepository,
            PedidoMapper pedidoMapper
    ) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public List<PedidoDto> listar() {
        return pedidoMapper.toDtoList(pedidoRepository.findAll());
    }

    @Override
    public PedidoDto obtenerPorId(Long idPedido) {
        return pedidoMapper.toDto(obtenerPedidoPorId(idPedido));
    }

    @Override
    public PedidoDto crear(PedidoDto pedidoDto) {
        PedidoEntity pedido = pedidoMapper.toEntity(pedidoDto);
        pedido.setIdPedido(null);
        pedido.setUsuario(obtenerUsuario(pedido));
        pedido.setFechaPedido(valorPorDefecto(pedido.getFechaPedido(), LocalDateTime.now()));
        pedido.setFechaRegistro(LocalDateTime.now());
        pedido.setFechaModifica(null);
        pedido.setActivo(valorPorDefecto(pedido.getActivo(), true));
        return pedidoMapper.toDto(pedidoRepository.save(pedido));
    }

    @Override
    public PedidoDto actualizar(Long idPedido, PedidoDto pedidoDto) {
        PedidoEntity pedido = pedidoMapper.toEntity(pedidoDto);
        PedidoEntity pedidoActual = obtenerPedidoPorId(idPedido);
        pedidoActual.setFechaPedido(pedido.getFechaPedido());
        pedidoActual.setTotal(pedido.getTotal());
        pedidoActual.setUsuario(obtenerUsuario(pedido));
        pedidoActual.setActivo(pedido.getActivo());
        pedidoActual.setUsuarioModifica(pedido.getUsuarioModifica());
        pedidoActual.setFechaModifica(LocalDateTime.now());
        return pedidoMapper.toDto(pedidoRepository.save(pedidoActual));
    }

    @Override
    public void eliminar(Long idPedido) {
        pedidoRepository.delete(obtenerPedidoPorId(idPedido));
    }

    private PedidoEntity obtenerPedidoPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
    }

    private UsuarioEntity obtenerUsuario(PedidoEntity pedido) {
        if (pedido.getUsuario() == null || pedido.getUsuario().getIdUsuario() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el id del usuario");
        }
        return usuarioRepository.findById(pedido.getUsuario().getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private Boolean valorPorDefecto(Boolean valor, Boolean valorDefecto) {
        return valor != null ? valor : valorDefecto;
    }

    private LocalDateTime valorPorDefecto(LocalDateTime valor, LocalDateTime valorDefecto) {
        return valor != null ? valor : valorDefecto;
    }
}
