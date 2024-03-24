package co.epvtecnologia.sistema_pos_v10.repos;

import co.epvtecnologia.sistema_pos_v10.domain.Factura;
import co.epvtecnologia.sistema_pos_v10.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Factura findFirstByProductos(Producto producto);

    List<Factura> findAllByProductos(Producto producto);

    boolean existsByCodigoFacturaIgnoreCase(String codigoFactura);

}
