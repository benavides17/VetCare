package com.vetcare.dominio.modelo;

import com.vetcare.dominio.enumeraciones.EstadoCita;
import com.vetcare.exception.ReglaNegocioException;
import java.time.LocalDateTime;

/**
 * Cita programada para una mascota y veterinario.
 */
public class Cita {

    private Long id;
    private LocalDateTime fechaHora;
    private String motivo;
    private EstadoCita estado;
    private Mascota mascota;
    private Veterinario veterinario;

    public Cita(Long id, LocalDateTime fechaHora, String motivo, Mascota mascota, Veterinario veterinario) {
        this(id, fechaHora, motivo, EstadoCita.PROGRAMADA, mascota, veterinario);
    }

    public Cita(Long id, LocalDateTime fechaHora, String motivo, EstadoCita estado,
                Mascota mascota, Veterinario veterinario) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.estado = estado == null ? EstadoCita.PROGRAMADA : estado;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void reprogramar(LocalDateTime nuevaFechaHora) {
        if (nuevaFechaHora == null || nuevaFechaHora.isBefore(LocalDateTime.now())) {
            throw new ReglaNegocioException("La nueva fecha de la cita debe ser futura.");
        }
        validarProgramada();
        this.fechaHora = nuevaFechaHora;
    }

    public void cancelar() {
        validarProgramada();
        this.estado = EstadoCita.CANCELADA;
    }

    public void marcarAtendida() {
        validarProgramada();
        this.estado = EstadoCita.ATENDIDA;
    }

    private void validarProgramada() {
        if (estado != EstadoCita.PROGRAMADA) {
            throw new ReglaNegocioException("Solo se pueden modificar citas programadas.");
        }
    }
}
