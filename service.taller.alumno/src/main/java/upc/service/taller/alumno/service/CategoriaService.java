package upc.service.taller.alumno.service;

import upc.service.taller.alumno.entity.CategoriaEntity;

import java.util.List;

public interface CategoriaService {

    List<CategoriaEntity> listar();

    CategoriaEntity obtenerPorId(Long idCategoria);

    CategoriaEntity crear(CategoriaEntity categoria);

    CategoriaEntity actualizar(Long idCategoria, CategoriaEntity categoria);

    void eliminar(Long idCategoria);
}
