package com.vetcare.aplicacion.controlador;

import com.vetcare.aplicacion.dto.UrgenciaDto;
import com.vetcare.aplicacion.dto.CambiarEstadoUrgenciaRequest;
import com.vetcare.aplicacion.dto.AsignarVeterinarioUrgenciaRequest;
import com.vetcare.aplicacion.servicio.UrgenciaService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/urgencias")
public class UrgenciaController {

    private final UrgenciaService servicio;

    public UrgenciaController(UrgenciaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<UrgenciaDto> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UrgenciaDto> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UrgenciaDto> guardar(@Valid @RequestBody UrgenciaDto dto) {
        UrgenciaDto creado = servicio.guardar(dto);
        return ResponseEntity
                .created(URI.create("/api/urgencias/" + creado.id()))
                .body(creado);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<UrgenciaDto> cambiarEstado(@PathVariable Long id,
            @Valid @RequestBody CambiarEstadoUrgenciaRequest solicitud) {
        return ResponseEntity.ok(servicio.cambiarEstado(id,
                solicitud.estado(), solicitud.destinoRemision()));
    }

    @PatchMapping("/{id}/veterinario")
    public ResponseEntity<UrgenciaDto> reasignarVeterinario(@PathVariable Long id,
            @Valid @RequestBody AsignarVeterinarioUrgenciaRequest solicitud) {
        return ResponseEntity.ok(servicio.reasignarVeterinario(id, solicitud.veterinarioId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
