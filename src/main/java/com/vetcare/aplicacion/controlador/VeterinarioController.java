package com.vetcare.aplicacion.controlador;

import com.vetcare.aplicacion.dto.VeterinarioDto;
import com.vetcare.aplicacion.servicio.VeterinarioService;
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
@RequestMapping("/veterinarios")
public class VeterinarioController {

    private final VeterinarioService servicio;

    public VeterinarioController(VeterinarioService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<VeterinarioDto> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioDto> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/documento")
    public ResponseEntity<VeterinarioDto> buscarPorDocumento(@RequestParam String numeroDocumento) {
        return servicio.buscarPorDocumento(numeroDocumento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VeterinarioDto> guardar(@jakarta.validation.Valid @RequestBody VeterinarioDto dto) {
        VeterinarioDto creado = servicio.guardar(dto);
        return ResponseEntity
                .created(URI.create("/api/veterinarios/" + creado.id()))
                .body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
