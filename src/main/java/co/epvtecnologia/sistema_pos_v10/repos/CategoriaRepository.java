package co.epvtecnologia.sistema_pos_v10.repos;

import co.epvtecnologia.sistema_pos_v10.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNombreCategoriaIgnoreCase(String nombreCategoria);

}
