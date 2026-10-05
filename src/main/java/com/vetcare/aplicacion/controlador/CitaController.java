package com.vetcare.aplicacion.controlador;

import com.vetcare.aplicacion.dto.CitaDto;
import com.vetcare.aplicacion.dto.ReprogramarCitaRequest;
import com.vetcare.aplicacion.servicio.CitaService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;

/** Controller dedicado al protocolo HTTP de citas (responsabilidad única). */
@RestController
@RequestMapping("/citas")
public class CitaController {

    private final CitaService servicio;

    public CitaController(CitaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<CitaDto> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CitaDto> guardar(@Valid @RequestBody CitaDto dto) {
        CitaDto creado = servicio.guardar(dto);
        return ResponseEntity
                .created(URI.create("/api/citas/" + creado.id()))
                .body(creado);
    }

    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<CitaDto> reprogramar(@PathVariable Long id,
                                               @Valid @RequestBody ReprogramarCitaRequest solicitud) {
        return ResponseEntity.ok(servicio.reprogramar(id, solicitud.fechaHora()));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<CitaDto> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.cancelar(id));
    }

    @PostMapping("/{id}/atender")
    public ResponseEntity<CitaDto> marcarAtendida(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.marcarAtendida(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
