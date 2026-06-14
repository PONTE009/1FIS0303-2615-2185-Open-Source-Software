package upc.service.taller.alumno.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.entity.CategoriaEntity;
import upc.service.taller.alumno.repository.CategoriaRepository;
import upc.service.taller.alumno.service.CategoriaService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<CategoriaEntity> listar() {
        return categoriaRepository.findAll();
    }

    @Override
    public CategoriaEntity obtenerPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));
    }

    @Override
    public CategoriaEntity crear(CategoriaEntity categoria) {
        LocalDateTime ahora = LocalDateTime.now();
        categoria.setIdCategoria(null);
        categoria.setFechaRegistro(ahora);
        categoria.setFechaModifica(null);
        categoria.setActivo(valorPorDefecto(categoria.getActivo(), true));
        categoria.setEstado(valorPorDefecto(categoria.getEstado(), true));
        return categoriaRepository.save(categoria);
    }

    @Override
    public CategoriaEntity actualizar(Long idCategoria, CategoriaEntity categoria) {
        CategoriaEntity categoriaActual = obtenerPorId(idCategoria);
        categoriaActual.setNombre(categoria.getNombre());
        categoriaActual.setDescripcion(categoria.getDescripcion());
        categoriaActual.setEstado(categoria.getEstado());
        categoriaActual.setActivo(categoria.getActivo());
        categoriaActual.setUsuarioModifica(categoria.getUsuarioModifica());
        categoriaActual.setFechaModifica(LocalDateTime.now());
        return categoriaRepository.save(categoriaActual);
    }

    @Override
    public void eliminar(Long idCategoria) {
        CategoriaEntity categoria = obtenerPorId(idCategoria);
        categoriaRepository.delete(categoria);
    }

    private Boolean valorPorDefecto(Boolean valor, Boolean valorDefecto) {
        return valor != null ? valor : valorDefecto;
    }
}
