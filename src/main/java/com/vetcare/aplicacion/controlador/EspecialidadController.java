package com.vetcare.aplicacion.controlador;

import com.vetcare.aplicacion.dto.EspecialidadDto;
import com.vetcare.aplicacion.servicio.EspecialidadService;
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
@RequestMapping("/especialidades")
public class EspecialidadController {

    private final EspecialidadService servicio;

    public EspecialidadController(EspecialidadService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<EspecialidadDto> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadDto> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EspecialidadDto> guardar(@jakarta.validation.Valid @RequestBody EspecialidadDto dto) {
        EspecialidadDto creado = servicio.guardar(dto);
        return ResponseEntity
                .created(URI.create("/api/especialidades/" + creado.id()))
                .body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
