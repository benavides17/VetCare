package com.vetcare.dominio.modelo;

import com.vetcare.dominio.enumeraciones.EstadoUrgencia;
import com.vetcare.dominio.enumeraciones.Prioridad;
import com.vetcare.exception.TransicionEstadoInvalidaException;
import com.vetcare.exception.VeterinarioNoDisponibleException;
import java.time.LocalDateTime;

/**
 * Urgencia especializada de Atencion que aplica sus transiciones y reglas de asignación (LSP).
 */
public class Urgencia extends Atencion {

    private LocalDateTime horaLlegada;
    private Prioridad prioridad;
    private EstadoUrgencia estado;
    private Veterinario veterinarioAsignado;
    private String destinoRemision;
    private Mascota mascota;

    public Urgencia(Long id, LocalDateTime fechaHora, String motivo, String sintomas,
                    LocalDateTime horaLlegada, Prioridad prioridad, Mascota mascota) {
        this(id, fechaHora, motivo, sintomas, horaLlegada, prioridad, mascota,
                EstadoUrgencia.RECIBIDA, null);
    }

    public Urgencia(Long id, LocalDateTime fechaHora, String motivo, String sintomas,
                    LocalDateTime horaLlegada, Prioridad prioridad, Mascota mascota,
                    EstadoUrgencia estado, Veterinario veterinarioAsignado) {
        super(id, fechaHora, motivo, sintomas);
        this.horaLlegada = horaLlegada;
        this.prioridad = prioridad;
        this.estado = estado == null ? EstadoUrgencia.RECIBIDA : estado;
        this.mascota = mascota;
        this.veterinarioAsignado = veterinarioAsignado;
    }

    public LocalDateTime getHoraLlegada() {
        return horaLlegada;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public EstadoUrgencia getEstado() {
        return estado;
    }

    public Veterinario getVeterinarioAsignado() {
        return veterinarioAsignado;
    }

    public String getDestinoRemision() {
        return destinoRemision;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void asignarVeterinario(Veterinario veterinario) {
        if (veterinario == null) {
            throw new IllegalArgumentException("El veterinario es obligatorio.");
        }
        if (!veterinario.estaDisponible()) {
            throw new VeterinarioNoDisponibleException("El veterinario no está disponible.");
        }
        cambiarEstado(EstadoUrgencia.EN_ATENCION);
        this.veterinarioAsignado = veterinario;
        veterinario.ocupar();
    }

    public void cambiarEstado(EstadoUrgencia nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser nulo.");
        }
        if (!estado.puedePasarA(nuevoEstado)) {
            throw new TransicionEstadoInvalidaException(
                    "No se puede cambiar de " + estado + " a " + nuevoEstado + ".");
        }
        this.estado = nuevoEstado;
    }

    public void reasignarVeterinario(Veterinario veterinario) {
        if (veterinario == null) {
            throw new IllegalArgumentException("El veterinario es obligatorio.");
        }
        if (veterinarioAsignado == veterinario) {
            return;
        }
        if (!veterinario.estaDisponible()) {
            throw new VeterinarioNoDisponibleException("El veterinario no está disponible.");
        }
        if (!esActiva()) {
            throw new TransicionEstadoInvalidaException(
                    "No se puede reasignar un veterinario a una urgencia cerrada.");
        }
        if (estado == EstadoUrgencia.EN_ESPERA) {
            cambiarEstado(EstadoUrgencia.EN_ATENCION);
        }
        if (veterinarioAsignado != null) {
            veterinarioAsignado.liberar();
        }
        this.veterinarioAsignado = veterinario;
        veterinario.ocupar();
    }

    public void remitir(String destino) {
        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException("El destino de remisión es obligatorio.");
        }
        cambiarEstado(EstadoUrgencia.REMITIDA);
        this.destinoRemision = destino;
    }

    public void finalizar() {
        cambiarEstado(EstadoUrgencia.FINALIZADA);
    }

    public boolean esActiva() {
        return estado.esActiva();
    }

    @Override
    public String tipo() {
        return "URGENCIA";
    }
}
