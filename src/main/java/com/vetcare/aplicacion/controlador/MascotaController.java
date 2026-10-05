package com.vetcare.aplicacion.controlador;

import com.vetcare.aplicacion.dto.MascotaDto;
import com.vetcare.aplicacion.servicio.MascotaService;
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

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    private final MascotaService servicio;

    public MascotaController(MascotaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<MascotaDto> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaDto> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MascotaDto> guardar(@jakarta.validation.Valid @RequestBody MascotaDto dto) {
        MascotaDto creado = servicio.guardar(dto);
        return ResponseEntity
                .created(URI.create("/api/mascotas/" + creado.id()))
                .body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
