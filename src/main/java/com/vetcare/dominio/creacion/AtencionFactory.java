package com.vetcare.dominio.creacion;

import com.vetcare.dominio.modelo.Cita;
import com.vetcare.dominio.modelo.Consulta;
import com.vetcare.dominio.modelo.Mascota;
import com.vetcare.dominio.modelo.Urgencia;
import com.vetcare.dominio.modelo.Veterinario;
import java.util.Objects;

/**
 * Factory para crear distintas atenciones clínicas del sistema.
 *
 * Patrón Factory (creación de objetos con lógica encapsulada).
 */
public class AtencionFactory {

    public Consulta crearConsulta(Mascota mascota, Veterinario veterinario, Cita cita) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota es obligatoria.");
        }
        if (veterinario == null) {
            throw new IllegalArgumentException("El veterinario es obligatorio.");
        }
        if (cita == null) {
            throw new IllegalArgumentException("La cita es obligatoria.");
        }
        if (cita.getMascota() == null || cita.getVeterinario() == null
                || !Objects.equals(cita.getMascota().getId(), mascota.getId())
                || !Objects.equals(cita.getVeterinario().getId(), veterinario.getId())) {
            throw new IllegalArgumentException("La mascota y el veterinario deben corresponder a la cita.");
        }
        return new Consulta(cita.getId(), cita.getFechaHora(), cita.getMotivo(),
                "Consulta atendida", cita);
    }

    public Urgencia crearUrgencia(SolicitudUrgencia solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud de urgencia es obligatoria.");
        }
        return new Urgencia(
                solicitud.id(),
                solicitud.fechaHora(),
                solicitud.motivo(),
                solicitud.sintomas(),
                solicitud.horaLlegada(),
                solicitud.prioridad(),
                solicitud.mascota()
        );
    }
}
