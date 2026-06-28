package upc.service.taller.alumno.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.dto.ProveedoresDto;
import upc.service.taller.alumno.entity.ProveedoresEntity;
import upc.service.taller.alumno.mapper.ProveedoresMapper;
import upc.service.taller.alumno.repository.ProveedoresRepository;
import upc.service.taller.alumno.service.ProveedoresService;

import java.util.List;

@Service
public class ProveedoresServiceImpl implements ProveedoresService {

    private final ProveedoresRepository proveedoresRepository;
    private final ProveedoresMapper proveedoresMapper;

    public ProveedoresServiceImpl(ProveedoresRepository proveedoresRepository, ProveedoresMapper proveedoresMapper) {
        this.proveedoresRepository = proveedoresRepository;
        this.proveedoresMapper = proveedoresMapper;
    }

    @Override
    public List<ProveedoresDto> listar() {
        return proveedoresMapper.toDtoList(proveedoresRepository.findAll());
    }

    @Override
    public ProveedoresDto obtenerPorId(Long idProveedor) {
        return proveedoresMapper.toDto(obtenerProveedorPorId(idProveedor));
    }

    @Override
    public ProveedoresDto crear(ProveedoresDto proveedorDto) {
        ProveedoresEntity proveedor = proveedoresMapper.toEntity(proveedorDto);
        proveedor.setIdProveedor(null);
        return proveedoresMapper.toDto(proveedoresRepository.save(proveedor));
    }

    @Override
    public ProveedoresDto actualizar(Long idProveedor, ProveedoresDto proveedorDto) {
        ProveedoresEntity proveedor = proveedoresMapper.toEntity(proveedorDto);
        ProveedoresEntity proveedorActual = obtenerProveedorPorId(idProveedor);
        proveedorActual.setRazonSocial(proveedor.getRazonSocial());
        proveedorActual.setRuc(proveedor.getRuc());
        proveedorActual.setCorreo(proveedor.getCorreo());
        proveedorActual.setTelefono(proveedor.getTelefono());
        proveedorActual.setDireccion(proveedor.getDireccion());
        return proveedoresMapper.toDto(proveedoresRepository.save(proveedorActual));
    }

    @Override
    public void eliminar(Long idProveedor) {
        proveedoresRepository.delete(obtenerProveedorPorId(idProveedor));
    }

    private ProveedoresEntity obtenerProveedorPorId(Long idProveedor) {
        return proveedoresRepository.findById(idProveedor)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado"));
    }
}
