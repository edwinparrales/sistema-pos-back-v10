package co.epvtecnologia.sistema_pos_v10.rest;

import co.epvtecnologia.sistema_pos_v10.model.FacturaDTO;
import co.epvtecnologia.sistema_pos_v10.service.FacturaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/facturas", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacturaResource {

    private final FacturaService facturaService;

    public FacturaResource(final FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> getAllFacturas() {
        return ResponseEntity.ok(facturaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> getFactura(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(facturaService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createFactura(@RequestBody @Valid final FacturaDTO facturaDTO) {
        final Long createdId = facturaService.create(facturaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFactura(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FacturaDTO facturaDTO) {
        facturaService.update(id, facturaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactura(@PathVariable(name = "id") final Long id) {
        facturaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
