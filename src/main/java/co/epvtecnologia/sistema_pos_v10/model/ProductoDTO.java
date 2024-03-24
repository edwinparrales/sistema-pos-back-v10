package co.epvtecnologia.sistema_pos_v10.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductoDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @ProductoCodigoBarrasUnique
    private String codigoBarras;

    @NotNull
    @Size(max = 255)
    @ProductoCodigoInternoUnique
    private String codigoInterno;

    @NotNull
    @Size(max = 255)
    private String nombreProdoucto;

    @NotNull
    private Integer cantidad;

    private Integer cantidadMin;

    private Integer cantidadMax;

    @NotNull
    private Double valorVenta;

    @NotNull
    private Double valorCompra;

    private Long categoria;

}
