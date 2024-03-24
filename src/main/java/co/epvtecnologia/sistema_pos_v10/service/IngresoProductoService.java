package co.epvtecnologia.sistema_pos_v10.service;

import co.epvtecnologia.sistema_pos_v10.domain.IngresoProducto;
import co.epvtecnologia.sistema_pos_v10.domain.Producto;
import co.epvtecnologia.sistema_pos_v10.model.IngresoProductoDTO;
import co.epvtecnologia.sistema_pos_v10.repos.IngresoProductoRepository;
import co.epvtecnologia.sistema_pos_v10.repos.ProductoRepository;
import co.epvtecnologia.sistema_pos_v10.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class IngresoProductoService {

    private final IngresoProductoRepository ingresoProductoRepository;
    private final ProductoRepository productoRepository;

    public IngresoProductoService(final IngresoProductoRepository ingresoProductoRepository,
            final ProductoRepository productoRepository) {
        this.ingresoProductoRepository = ingresoProductoRepository;
        this.productoRepository = productoRepository;
    }

    public List<IngresoProductoDTO> findAll() {
        final List<IngresoProducto> ingresoProductoes = ingresoProductoRepository.findAll(Sort.by("id"));
        return ingresoProductoes.stream()
                .map(ingresoProducto -> mapToDTO(ingresoProducto, new IngresoProductoDTO()))
                .toList();
    }

    public IngresoProductoDTO get(final Long id) {
        return ingresoProductoRepository.findById(id)
                .map(ingresoProducto -> mapToDTO(ingresoProducto, new IngresoProductoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final IngresoProductoDTO ingresoProductoDTO) {
        final IngresoProducto ingresoProducto = new IngresoProducto();
        mapToEntity(ingresoProductoDTO, ingresoProducto);
        return ingresoProductoRepository.save(ingresoProducto).getId();
    }

    public void update(final Long id, final IngresoProductoDTO ingresoProductoDTO) {
        final IngresoProducto ingresoProducto = ingresoProductoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ingresoProductoDTO, ingresoProducto);
        ingresoProductoRepository.save(ingresoProducto);
    }

    public void delete(final Long id) {
        ingresoProductoRepository.deleteById(id);
    }

    private IngresoProductoDTO mapToDTO(final IngresoProducto ingresoProducto,
            final IngresoProductoDTO ingresoProductoDTO) {
        ingresoProductoDTO.setId(ingresoProducto.getId());
        ingresoProductoDTO.setFecha(ingresoProducto.getFecha());
        ingresoProductoDTO.setNombreProveedor(ingresoProducto.getNombreProveedor());
        ingresoProductoDTO.setNumFactProveedor(ingresoProducto.getNumFactProveedor());
        ingresoProductoDTO.setProductos(ingresoProducto.getProductos().stream()
                .map(producto -> producto.getId())
                .toList());
        return ingresoProductoDTO;
    }

    private IngresoProducto mapToEntity(final IngresoProductoDTO ingresoProductoDTO,
            final IngresoProducto ingresoProducto) {
        ingresoProducto.setFecha(ingresoProductoDTO.getFecha());
        ingresoProducto.setNombreProveedor(ingresoProductoDTO.getNombreProveedor());
        ingresoProducto.setNumFactProveedor(ingresoProductoDTO.getNumFactProveedor());
        final List<Producto> productos = productoRepository.findAllById(
                ingresoProductoDTO.getProductos() == null ? Collections.emptyList() : ingresoProductoDTO.getProductos());
        if (productos.size() != (ingresoProductoDTO.getProductos() == null ? 0 : ingresoProductoDTO.getProductos().size())) {
            throw new NotFoundException("one of productos not found");
        }
        ingresoProducto.setProductos(new HashSet<>(productos));
        return ingresoProducto;
    }

}
