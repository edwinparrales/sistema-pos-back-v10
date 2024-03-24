package co.epvtecnologia.sistema_pos_v10.repos;

import co.epvtecnologia.sistema_pos_v10.domain.Categoria;
import co.epvtecnologia.sistema_pos_v10.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Producto findFirstByCategoria(Categoria categoria);

    boolean existsByCodigoBarrasIgnoreCase(String codigoBarras);

    boolean existsByCodigoInternoIgnoreCase(String codigoInterno);

}
