package upc.service.taller.alumno.service;

import upc.service.taller.alumno.dto.CategoriaDto;

import java.util.List;

public interface CategoriaService {

    List<CategoriaDto> listar();

    CategoriaDto obtenerPorId(Long idCategoria);

    CategoriaDto crear(CategoriaDto categoria);

    CategoriaDto actualizar(Long idCategoria, CategoriaDto categoria);

    void eliminar(Long idCategoria);
}
