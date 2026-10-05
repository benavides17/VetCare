package com.vetcare.aplicacion.controlador;

import com.vetcare.aplicacion.dto.TratamientoDto;
import com.vetcare.aplicacion.servicio.TratamientoService;
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
@RequestMapping("/tratamientos")
public class TratamientoController {

    private final TratamientoService servicio;

    public TratamientoController(TratamientoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<TratamientoDto> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TratamientoDto> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/atencion/{atencionId}")
    public List<TratamientoDto> buscarPorAtencionId(@PathVariable Long atencionId) {
        return servicio.buscarPorAtencionId(atencionId);
    }

    @PostMapping
    public ResponseEntity<TratamientoDto> guardar(@jakarta.validation.Valid @RequestBody TratamientoDto dto) {
        TratamientoDto creado = servicio.guardar(dto);
        return ResponseEntity
                .created(URI.create("/api/tratamientos/" + creado.id()))
                .body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
