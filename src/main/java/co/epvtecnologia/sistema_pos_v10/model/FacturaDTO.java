package co.epvtecnologia.sistema_pos_v10.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FacturaDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @FacturaCodigoFacturaUnique
    private String codigoFactura;

    @NotNull
    private LocalDateTime fecha;

    @NotNull
    private Boolean impIva;

    @NotNull
    @Size(max = 255)
    private String docCliente;

    @NotNull
    @Size(max = 255)
    private String nombreCliente;

    @Size(max = 255)
    private String nombreVendedor;

    private List<Long> productos;

}
