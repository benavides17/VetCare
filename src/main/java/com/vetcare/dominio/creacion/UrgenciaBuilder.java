package com.vetcare.dominio.creacion;

import com.vetcare.dominio.enumeraciones.Prioridad;
import com.vetcare.dominio.modelo.Mascota;
import com.vetcare.dominio.modelo.Urgencia;
import java.time.LocalDateTime;

/**
 * Builder para la entidad Urgencia con validación de datos obligatorios.
 */
public class UrgenciaBuilder {

    private Long id;
    private LocalDateTime fechaHora;
    private String motivo;
    private String sintomas;
    private LocalDateTime horaLlegada;
    private Prioridad prioridad;
    private Mascota mascota;

    public UrgenciaBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UrgenciaBuilder fechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
        return this;
    }

    public UrgenciaBuilder motivo(String motivo) {
        this.motivo = motivo;
        return this;
    }

    public UrgenciaBuilder sintomas(String sintomas) {
        this.sintomas = sintomas;
        return this;
    }

    public UrgenciaBuilder horaLlegada(LocalDateTime horaLlegada) {
        this.horaLlegada = horaLlegada;
        return this;
    }

    public UrgenciaBuilder prioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public UrgenciaBuilder mascota(Mascota mascota) {
        this.mascota = mascota;
        return this;
    }

    public Urgencia build() {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha de la urgencia es obligatoria.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("El motivo es obligatorio.");
        }
        if (sintomas == null || sintomas.isBlank()) {
            throw new IllegalArgumentException("Los síntomas son obligatorios.");
        }
        if (horaLlegada == null) {
            throw new IllegalArgumentException("La hora de llegada es obligatoria.");
        }
        if (prioridad == null) {
            throw new IllegalArgumentException("La prioridad es obligatoria.");
        }
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota es obligatoria.");
        }
        return new Urgencia(id, fechaHora, motivo, sintomas, horaLlegada, prioridad, mascota);
    }
}
