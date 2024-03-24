package co.epvtecnologia.sistema_pos_v10.repos;

import co.epvtecnologia.sistema_pos_v10.domain.Imagen;
import co.epvtecnologia.sistema_pos_v10.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImagenRepository extends JpaRepository<Imagen, Long> {

    Imagen findFirstByProducto(Producto producto);

}
