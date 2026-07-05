package upc.service.taller.alumno.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upc.service.taller.alumno.dto.CategoriaDto;
import upc.service.taller.alumno.entity.CategoriaEntity;
import upc.service.taller.alumno.mapper.CategoriaMapper;
import upc.service.taller.alumno.repository.CategoriaRepository;
import upc.service.taller.alumno.service.AuditoriaService;
import upc.service.taller.alumno.service.CategoriaService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private static final String TABLA_CATEGORIAS = "tbl_categorias";
    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final AuditoriaService auditoriaService;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper, AuditoriaService auditoriaService) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
        this.auditoriaService = auditoriaService;
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
    @Transactional
    public CategoriaDto crear(CategoriaDto categoriaDto) {
        CategoriaEntity categoria = categoriaMapper.toEntity(categoriaDto);
        LocalDateTime ahora = LocalDateTime.now();
        categoria.setIdCategoria(null);
        categoria.setFechaRegistro(ahora);
        categoria.setFechaModifica(null);
        categoria.setActivo(valorPorDefecto(categoria.getActivo(), true));
        categoria.setEstado(valorPorDefecto(categoria.getEstado(), true));
        CategoriaEntity categoriaGuardada = categoriaRepository.save(categoria);
        auditoriaService.registrar(TABLA_CATEGORIAS,"INSERT",String.valueOf(categoriaGuardada.getIdCategoria()),categoriaGuardada,null);

        return categoriaMapper.toDto(categoriaGuardada);
    }

    @Override
    @Transactional
    public CategoriaDto actualizar(Long idCategoria, CategoriaDto categoriaDto) {
        CategoriaEntity categoria = categoriaMapper.toEntity(categoriaDto);
        CategoriaEntity categoriaActual = obtenerCategoriaPorId(idCategoria);
        CategoriaEntity categoriaAnterior = copiarCategoria(categoriaActual);
        categoriaActual.setNombre(categoria.getNombre());
        categoriaActual.setDescripcion(categoria.getDescripcion());
        categoriaActual.setEstado(categoria.getEstado());
        categoriaActual.setActivo(categoria.getActivo());
        categoriaActual.setUsuarioModifica(categoria.getUsuarioModifica());
        categoriaActual.setFechaModifica(LocalDateTime.now());
        CategoriaEntity categoriaGuardada = categoriaRepository.save(categoriaActual);
        auditoriaService.registrar(TABLA_CATEGORIAS,"UPDATE",String.valueOf(categoriaGuardada.getIdCategoria()),categoriaGuardada,categoriaAnterior);

        return categoriaMapper.toDto(categoriaGuardada);
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

    private CategoriaEntity copiarCategoria(CategoriaEntity categoria) {
        CategoriaEntity categoriaCopia = new CategoriaEntity();
        categoriaCopia.setIdCategoria(categoria.getIdCategoria());
        categoriaCopia.setNombre(categoria.getNombre());
        categoriaCopia.setDescripcion(categoria.getDescripcion());
        categoriaCopia.setEstado(categoria.getEstado());
        categoriaCopia.setActivo(categoria.getActivo());
        categoriaCopia.setUsuarioModifica(categoria.getUsuarioModifica());
        categoriaCopia.setFechaModifica(LocalDateTime.now());
        return categoriaCopia;
    }
}
