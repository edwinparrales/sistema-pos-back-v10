package co.epvtecnologia.sistema_pos_v10.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class IngresoProductoDTO {

    private Long id;

    private LocalDateTime fecha;

    @NotNull
    @Size(max = 255)
    private String nombreProveedor;

    @Size(max = 255)
    private String numFactProveedor;

    private List<Long> productos;

}
