package com.vetcare.aplicacion.controlador;

import com.vetcare.aplicacion.dto.HistorialMedicoDto;
import com.vetcare.aplicacion.servicio.HistorialMedicoService;
import java.util.Optional;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/historiales")
public class HistorialMedicoController {

    private final HistorialMedicoService servicio;

    public HistorialMedicoController(HistorialMedicoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<HistorialMedicoDto> buscarPorMascotaId(@PathVariable Long mascotaId) {
        Optional<HistorialMedicoDto> historial = servicio.buscarPorMascotaId(mascotaId);
        return historial.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mascota/{mascotaId}/atenciones")
    public ResponseEntity<HistorialMedicoDto> filtrarAtenciones(
            @PathVariable Long mascotaId,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta,
            @RequestParam(required = false) Long veterinarioId,
            @RequestParam(required = false) String diagnostico) {
        return servicio.buscarPorMascotaId(mascotaId, tipo, desde, hasta,
                        veterinarioId, diagnostico)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
