package co.epvtecnologia.sistema_pos_v10.service;

import co.epvtecnologia.sistema_pos_v10.domain.Categoria;
import co.epvtecnologia.sistema_pos_v10.domain.Imagen;
import co.epvtecnologia.sistema_pos_v10.domain.Producto;
import co.epvtecnologia.sistema_pos_v10.model.ProductoDTO;
import co.epvtecnologia.sistema_pos_v10.repos.CategoriaRepository;
import co.epvtecnologia.sistema_pos_v10.repos.FacturaRepository;
import co.epvtecnologia.sistema_pos_v10.repos.ImagenRepository;
import co.epvtecnologia.sistema_pos_v10.repos.IngresoProductoRepository;
import co.epvtecnologia.sistema_pos_v10.repos.ProductoRepository;
import co.epvtecnologia.sistema_pos_v10.util.NotFoundException;
import co.epvtecnologia.sistema_pos_v10.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final IngresoProductoRepository ingresoProductoRepository;
    private final FacturaRepository facturaRepository;
    private final ImagenRepository imagenRepository;

    public ProductoService(final ProductoRepository productoRepository,
            final CategoriaRepository categoriaRepository,
            final IngresoProductoRepository ingresoProductoRepository,
            final FacturaRepository facturaRepository, final ImagenRepository imagenRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.ingresoProductoRepository = ingresoProductoRepository;
        this.facturaRepository = facturaRepository;
        this.imagenRepository = imagenRepository;
    }

    public List<ProductoDTO> findAll() {
        final List<Producto> productoes = productoRepository.findAll(Sort.by("id"));
        return productoes.stream()
                .map(producto -> mapToDTO(producto, new ProductoDTO()))
                .toList();
    }

    public ProductoDTO get(final Long id) {
        return productoRepository.findById(id)
                .map(producto -> mapToDTO(producto, new ProductoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProductoDTO productoDTO) {
        final Producto producto = new Producto();
        mapToEntity(productoDTO, producto);
        return productoRepository.save(producto).getId();
    }

    public void update(final Long id, final ProductoDTO productoDTO) {
        final Producto producto = productoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productoDTO, producto);
        productoRepository.save(producto);
    }

    public void delete(final Long id) {
        final Producto producto = productoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        ingresoProductoRepository.findAllByProductos(producto)
                .forEach(ingresoProducto -> ingresoProducto.getProductos().remove(producto));
        facturaRepository.findAllByProductos(producto)
                .forEach(factura -> factura.getProductos().remove(producto));
        productoRepository.delete(producto);
    }

    private ProductoDTO mapToDTO(final Producto producto, final ProductoDTO productoDTO) {
        productoDTO.setId(producto.getId());
        productoDTO.setCodigoBarras(producto.getCodigoBarras());
        productoDTO.setCodigoInterno(producto.getCodigoInterno());
        productoDTO.setNombreProdoucto(producto.getNombreProdoucto());
        productoDTO.setCantidad(producto.getCantidad());
        productoDTO.setCantidadMin(producto.getCantidadMin());
        productoDTO.setCantidadMax(producto.getCantidadMax());
        productoDTO.setValorVenta(producto.getValorVenta());
        productoDTO.setValorCompra(producto.getValorCompra());
        productoDTO.setCategoria(producto.getCategoria() == null ? null : producto.getCategoria().getId());
        return productoDTO;
    }

    private Producto mapToEntity(final ProductoDTO productoDTO, final Producto producto) {
        producto.setCodigoBarras(productoDTO.getCodigoBarras());
        producto.setCodigoInterno(productoDTO.getCodigoInterno());
        producto.setNombreProdoucto(productoDTO.getNombreProdoucto());
        producto.setCantidad(productoDTO.getCantidad());
        producto.setCantidadMin(productoDTO.getCantidadMin());
        producto.setCantidadMax(productoDTO.getCantidadMax());
        producto.setValorVenta(productoDTO.getValorVenta());
        producto.setValorCompra(productoDTO.getValorCompra());
        final Categoria categoria = productoDTO.getCategoria() == null ? null : categoriaRepository.findById(productoDTO.getCategoria())
                .orElseThrow(() -> new NotFoundException("categoria not found"));
        producto.setCategoria(categoria);
        return producto;
    }

    public boolean codigoBarrasExists(final String codigoBarras) {
        return productoRepository.existsByCodigoBarrasIgnoreCase(codigoBarras);
    }

    public boolean codigoInternoExists(final String codigoInterno) {
        return productoRepository.existsByCodigoInternoIgnoreCase(codigoInterno);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Producto producto = productoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Imagen productoImagen = imagenRepository.findFirstByProducto(producto);
        if (productoImagen != null) {
            referencedWarning.setKey("producto.imagen.producto.referenced");
            referencedWarning.addParam(productoImagen.getId());
            return referencedWarning;
        }
        return null;
    }

}
