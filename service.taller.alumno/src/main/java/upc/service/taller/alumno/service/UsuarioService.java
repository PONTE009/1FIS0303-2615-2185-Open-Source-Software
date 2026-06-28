package upc.service.taller.alumno.service;

import upc.service.taller.alumno.dto.UsuarioDto;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDto> listar();

    UsuarioDto obtenerPorId(Long idUsuario);

    UsuarioDto crear(UsuarioDto usuario);

    UsuarioDto actualizar(Long idUsuario, UsuarioDto usuario);

    void eliminar(Long idUsuario);
}
