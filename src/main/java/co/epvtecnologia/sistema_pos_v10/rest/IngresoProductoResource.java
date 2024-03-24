package co.epvtecnologia.sistema_pos_v10.rest;

import co.epvtecnologia.sistema_pos_v10.model.IngresoProductoDTO;
import co.epvtecnologia.sistema_pos_v10.service.IngresoProductoService;
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
@RequestMapping(value = "/api/ingresoProductos", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngresoProductoResource {

    private final IngresoProductoService ingresoProductoService;

    public IngresoProductoResource(final IngresoProductoService ingresoProductoService) {
        this.ingresoProductoService = ingresoProductoService;
    }

    @GetMapping
    public ResponseEntity<List<IngresoProductoDTO>> getAllIngresoProductos() {
        return ResponseEntity.ok(ingresoProductoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngresoProductoDTO> getIngresoProducto(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(ingresoProductoService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createIngresoProducto(
            @RequestBody @Valid final IngresoProductoDTO ingresoProductoDTO) {
        final Long createdId = ingresoProductoService.create(ingresoProductoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateIngresoProducto(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final IngresoProductoDTO ingresoProductoDTO) {
        ingresoProductoService.update(id, ingresoProductoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngresoProducto(@PathVariable(name = "id") final Long id) {
        ingresoProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}