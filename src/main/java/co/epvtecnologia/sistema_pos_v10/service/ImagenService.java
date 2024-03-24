package co.epvtecnologia.sistema_pos_v10.service;

import co.epvtecnologia.sistema_pos_v10.domain.Imagen;
import co.epvtecnologia.sistema_pos_v10.domain.Producto;
import co.epvtecnologia.sistema_pos_v10.model.ImagenDTO;
import co.epvtecnologia.sistema_pos_v10.repos.ImagenRepository;
import co.epvtecnologia.sistema_pos_v10.repos.ProductoRepository;
import co.epvtecnologia.sistema_pos_v10.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ImagenService {

    private final ImagenRepository imagenRepository;
    private final ProductoRepository productoRepository;

    public ImagenService(final ImagenRepository imagenRepository,
            final ProductoRepository productoRepository) {
        this.imagenRepository = imagenRepository;
        this.productoRepository = productoRepository;
    }

    public List<ImagenDTO> findAll() {
        final List<Imagen> imagens = imagenRepository.findAll(Sort.by("id"));
        return imagens.stream()
                .map(imagen -> mapToDTO(imagen, new ImagenDTO()))
                .toList();
    }

    public ImagenDTO get(final Long id) {
        return imagenRepository.findById(id)
                .map(imagen -> mapToDTO(imagen, new ImagenDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ImagenDTO imagenDTO) {
        final Imagen imagen = new Imagen();
        mapToEntity(imagenDTO, imagen);
        return imagenRepository.save(imagen).getId();
    }

    public void update(final Long id, final ImagenDTO imagenDTO) {
        final Imagen imagen = imagenRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(imagenDTO, imagen);
        imagenRepository.save(imagen);
    }

    public void delete(final Long id) {
        imagenRepository.deleteById(id);
    }

    private ImagenDTO mapToDTO(final Imagen imagen, final ImagenDTO imagenDTO) {
        imagenDTO.setId(imagen.getId());
        imagenDTO.setUrlImagen(imagen.getUrlImagen());
        imagenDTO.setIdProducto(imagen.getIdProducto());
        imagenDTO.setProducto(imagen.getProducto() == null ? null : imagen.getProducto().getId());
        return imagenDTO;
    }

    private Imagen mapToEntity(final ImagenDTO imagenDTO, final Imagen imagen) {
        imagen.setUrlImagen(imagenDTO.getUrlImagen());
        imagen.setIdProducto(imagenDTO.getIdProducto());
        final Producto producto = imagenDTO.getProducto() == null ? null : productoRepository.findById(imagenDTO.getProducto())
                .orElseThrow(() -> new NotFoundException("producto not found"));
        imagen.setProducto(producto);
        return imagen;
    }

}
