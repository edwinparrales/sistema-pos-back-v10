package co.epvtecnologia.sistema_pos_v10.repos;

import co.epvtecnologia.sistema_pos_v10.domain.IngresoProducto;
import co.epvtecnologia.sistema_pos_v10.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IngresoProductoRepository extends JpaRepository<IngresoProducto, Long> {

    IngresoProducto findFirstByProductos(Producto producto);

    List<IngresoProducto> findAllByProductos(Producto producto);

}
