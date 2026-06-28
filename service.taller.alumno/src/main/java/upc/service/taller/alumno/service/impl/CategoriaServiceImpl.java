package upc.service.taller.alumno.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.dto.CategoriaDto;
import upc.service.taller.alumno.entity.CategoriaEntity;
import upc.service.taller.alumno.mapper.CategoriaMapper;
import upc.service.taller.alumno.repository.CategoriaRepository;
import upc.service.taller.alumno.service.CategoriaService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Override
    public List<CategoriaDto> listar() {
        return categoriaMapper.toDtoList(categoriaRepository.findAll());
    }

    @Override
    public CategoriaDto obtenerPorId(Long idCategoria) {
        return categoriaMapper.toDto(obtenerCategoriaPorId(idCategoria));
    }

    @Override
    public CategoriaDto crear(CategoriaDto categoriaDto) {
        CategoriaEntity categoria = categoriaMapper.toEntity(categoriaDto);
        LocalDateTime ahora = LocalDateTime.now();
        categoria.setIdCategoria(null);
        categoria.setFechaRegistro(ahora);
        categoria.setFechaModifica(null);
        categoria.setActivo(valorPorDefecto(categoria.getActivo(), true));
        categoria.setEstado(valorPorDefecto(categoria.getEstado(), true));
        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaDto actualizar(Long idCategoria, CategoriaDto categoriaDto) {
        CategoriaEntity categoria = categoriaMapper.toEntity(categoriaDto);
        CategoriaEntity categoriaActual = obtenerCategoriaPorId(idCategoria);
        categoriaActual.setNombre(categoria.getNombre());
        categoriaActual.setDescripcion(categoria.getDescripcion());
        categoriaActual.setEstado(categoria.getEstado());
        categoriaActual.setActivo(categoria.getActivo());
        categoriaActual.setUsuarioModifica(categoria.getUsuarioModifica());
        categoriaActual.setFechaModifica(LocalDateTime.now());
        return categoriaMapper.toDto(categoriaRepository.save(categoriaActual));
    }

    @Override
    public void eliminar(Long idCategoria) {
        CategoriaEntity categoria = obtenerCategoriaPorId(idCategoria);
        categoriaRepository.delete(categoria);
    }

    private CategoriaEntity obtenerCategoriaPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));
    }

    private Boolean valorPorDefecto(Boolean valor, Boolean valorDefecto) {
        return valor != null ? valor : valorDefecto;
    }
}
