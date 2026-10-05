package com.vetcare.aplicacion.controlador;

import com.vetcare.aplicacion.dto.PropietarioDto;
import com.vetcare.aplicacion.servicio.PropietarioService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/propietarios")
public class PropietarioController {

    private final PropietarioService servicio;

    public PropietarioController(PropietarioService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<PropietarioDto> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropietarioDto> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/documento")
    public ResponseEntity<PropietarioDto> buscarPorDocumento(@RequestParam String numeroDocumento) {
        return servicio.buscarPorDocumento(numeroDocumento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PropietarioDto> guardar(@jakarta.validation.Valid @RequestBody PropietarioDto dto) {
        PropietarioDto creado = servicio.guardar(dto);
        return ResponseEntity
                .created(URI.create("/api/propietarios/" + creado.id()))
                .body(creado);
    }

    @PostMapping("/{propietarioId}/mascotas/{mascotaId}")
    public ResponseEntity<Void> asociarMascota(@PathVariable Long propietarioId,
                                               @PathVariable Long mascotaId) {
        servicio.asociarMascota(propietarioId, mascotaId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{propietarioId}/mascotas/{mascotaId}")
    public ResponseEntity<Void> desasociarMascota(@PathVariable Long propietarioId,
                                                  @PathVariable Long mascotaId) {
        servicio.desasociarMascota(propietarioId, mascotaId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
