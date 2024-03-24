package co.epvtecnologia.sistema_pos_v10.service;

import co.epvtecnologia.sistema_pos_v10.domain.Categoria;
import co.epvtecnologia.sistema_pos_v10.domain.Producto;
import co.epvtecnologia.sistema_pos_v10.model.CategoriaDTO;
import co.epvtecnologia.sistema_pos_v10.repos.CategoriaRepository;
import co.epvtecnologia.sistema_pos_v10.repos.ProductoRepository;
import co.epvtecnologia.sistema_pos_v10.util.NotFoundException;
import co.epvtecnologia.sistema_pos_v10.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaService(final CategoriaRepository categoriaRepository,
            final ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    public List<CategoriaDTO> findAll() {
        final List<Categoria> categorias = categoriaRepository.findAll(Sort.by("id"));
        return categorias.stream()
                .map(categoria -> mapToDTO(categoria, new CategoriaDTO()))
                .toList();
    }

    public CategoriaDTO get(final Long id) {
        return categoriaRepository.findById(id)
                .map(categoria -> mapToDTO(categoria, new CategoriaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CategoriaDTO categoriaDTO) {
        final Categoria categoria = new Categoria();
        mapToEntity(categoriaDTO, categoria);
        return categoriaRepository.save(categoria).getId();
    }

    public void update(final Long id, final CategoriaDTO categoriaDTO) {
        final Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoriaDTO, categoria);
        categoriaRepository.save(categoria);
    }

    public void delete(final Long id) {
        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO mapToDTO(final Categoria categoria, final CategoriaDTO categoriaDTO) {
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombreCategoria(categoria.getNombreCategoria());
        categoriaDTO.setUrlImagen(categoria.getUrlImagen());
        return categoriaDTO;
    }

    private Categoria mapToEntity(final CategoriaDTO categoriaDTO, final Categoria categoria) {
        categoria.setNombreCategoria(categoriaDTO.getNombreCategoria());
        categoria.setUrlImagen(categoriaDTO.getUrlImagen());
        return categoria;
    }

    public boolean nombreCategoriaExists(final String nombreCategoria) {
        return categoriaRepository.existsByNombreCategoriaIgnoreCase(nombreCategoria);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Producto categoriaProducto = productoRepository.findFirstByCategoria(categoria);
        if (categoriaProducto != null) {
            referencedWarning.setKey("categoria.producto.categoria.referenced");
            referencedWarning.addParam(categoriaProducto.getId());
            return referencedWarning;
        }
        return null;
    }

}
