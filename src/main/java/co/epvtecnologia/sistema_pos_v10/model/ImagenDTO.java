package co.epvtecnologia.sistema_pos_v10.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ImagenDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String urlImagen;

    @NotNull
    private Long idProducto;

    private Long producto;

}
