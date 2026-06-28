package upc.service.taller.alumno.service;

import upc.service.taller.alumno.dto.ProveedoresDto;

import java.util.List;

public interface ProveedoresService {

    List<ProveedoresDto> listar();

    ProveedoresDto obtenerPorId(Long idProveedor);

    ProveedoresDto crear(ProveedoresDto proveedor);

    ProveedoresDto actualizar(Long idProveedor, ProveedoresDto proveedor);

    void eliminar(Long idProveedor);
}
