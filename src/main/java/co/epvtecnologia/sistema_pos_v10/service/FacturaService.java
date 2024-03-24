package co.epvtecnologia.sistema_pos_v10.service;

import co.epvtecnologia.sistema_pos_v10.domain.Factura;
import co.epvtecnologia.sistema_pos_v10.domain.Producto;
import co.epvtecnologia.sistema_pos_v10.model.FacturaDTO;
import co.epvtecnologia.sistema_pos_v10.repos.FacturaRepository;
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
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;

    public FacturaService(final FacturaRepository facturaRepository,
            final ProductoRepository productoRepository) {
        this.facturaRepository = facturaRepository;
        this.productoRepository = productoRepository;
    }

    public List<FacturaDTO> findAll() {
        final List<Factura> facturas = facturaRepository.findAll(Sort.by("id"));
        return facturas.stream()
                .map(factura -> mapToDTO(factura, new FacturaDTO()))
                .toList();
    }

    public FacturaDTO get(final Long id) {
        return facturaRepository.findById(id)
                .map(factura -> mapToDTO(factura, new FacturaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FacturaDTO facturaDTO) {
        final Factura factura = new Factura();
        mapToEntity(facturaDTO, factura);
        return facturaRepository.save(factura).getId();
    }

    public void update(final Long id, final FacturaDTO facturaDTO) {
        final Factura factura = facturaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(facturaDTO, factura);
        facturaRepository.save(factura);
    }

    public void delete(final Long id) {
        facturaRepository.deleteById(id);
    }

    private FacturaDTO mapToDTO(final Factura factura, final FacturaDTO facturaDTO) {
        facturaDTO.setId(factura.getId());
        facturaDTO.setCodigoFactura(factura.getCodigoFactura());
        facturaDTO.setFecha(factura.getFecha());
        facturaDTO.setImpIva(factura.getImpIva());
        facturaDTO.setDocCliente(factura.getDocCliente());
        facturaDTO.setNombreCliente(factura.getNombreCliente());
        facturaDTO.setNombreVendedor(factura.getNombreVendedor());
        facturaDTO.setProductos(factura.getProductos().stream()
                .map(producto -> producto.getId())
                .toList());
        return facturaDTO;
    }

    private Factura mapToEntity(final FacturaDTO facturaDTO, final Factura factura) {
        factura.setCodigoFactura(facturaDTO.getCodigoFactura());
        factura.setFecha(facturaDTO.getFecha());
        factura.setImpIva(facturaDTO.getImpIva());
        factura.setDocCliente(facturaDTO.getDocCliente());
        factura.setNombreCliente(facturaDTO.getNombreCliente());
        factura.setNombreVendedor(facturaDTO.getNombreVendedor());
        final List<Producto> productos = productoRepository.findAllById(
                facturaDTO.getProductos() == null ? Collections.emptyList() : facturaDTO.getProductos());
        if (productos.size() != (facturaDTO.getProductos() == null ? 0 : facturaDTO.getProductos().size())) {
            throw new NotFoundException("one of productos not found");
        }
        factura.setProductos(new HashSet<>(productos));
        return factura;
    }

    public boolean codigoFacturaExists(final String codigoFactura) {
        return facturaRepository.existsByCodigoFacturaIgnoreCase(codigoFactura);
    }

}
