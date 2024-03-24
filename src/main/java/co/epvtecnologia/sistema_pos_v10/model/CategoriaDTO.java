package co.epvtecnologia.sistema_pos_v10.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategoriaDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @CategoriaNombreCategoriaUnique
    private String nombreCategoria;

    @Size(max = 255)
    private String urlImagen;

}
