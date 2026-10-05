package com.vetcare.dominio.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Aggregate root que centraliza el historial clínico de una mascota.
 */
public class HistorialMedico {

    private Long id;
    private Mascota mascota;
    private final List<Atencion> atenciones = new ArrayList<>();

    public HistorialMedico(Long id, Mascota mascota) {
        this.id = id;
        this.mascota = mascota;
    }

    public Long getId() {
        return id;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public List<Atencion> getAtenciones() {
        return List.copyOf(atenciones);
    }

    public void registrar(Atencion atencion) {
        if (atencion == null) {
            throw new IllegalArgumentException("La atención no puede ser nula.");
        }
        if (atenciones.contains(atencion)) {
            throw new IllegalArgumentException("La atención ya se encuentra registrada en el historial.");
        }
        atenciones.add(atencion);
    }

    public List<Atencion> cronologico() {
        return atenciones.stream()
                .sorted(Comparator.comparing(Atencion::getFechaHora))
                .toList();
    }

    public List<Atencion> filtrar(String tipo, LocalDate desde, LocalDate hasta, Veterinario veterinario, String diagnostico) {
        return atenciones.stream()
                .filter(a -> tipo == null || a.tipo().equalsIgnoreCase(tipo))
                .filter(a -> desde == null || !a.getFechaHora().toLocalDate().isBefore(desde))
                .filter(a -> hasta == null || !a.getFechaHora().toLocalDate().isAfter(hasta))
                .filter(a -> veterinario == null || (a instanceof Consulta c && c.getCita() != null && c.getCita().getVeterinario() != null
                        && c.getCita().getVeterinario().equals(veterinario))
                        || (a instanceof Urgencia u && u.getVeterinarioAsignado() != null && u.getVeterinarioAsignado().equals(veterinario)))
                .filter(a -> diagnostico == null || (a.getDiagnostico() != null && a.getDiagnostico().toLowerCase().contains(diagnostico.toLowerCase())))
                .toList();
    }
}
