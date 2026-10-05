/**
 * Evento inmutable que describe un cambio de estado en una urgencia.
 */
package com.vetcare.dominio.valores;

import com.vetcare.dominio.enumeraciones.EstadoUrgencia;
import java.time.OffsetDateTime;

public record EventoUrgencia(Long urgenciaId,
                            EstadoUrgencia estadoAnterior,
                            EstadoUrgencia estadoNuevo,
                            Long veterinarioId,
                            OffsetDateTime ocurridoEn) {

    public EventoUrgencia {
        if (urgenciaId == null) {
            throw new IllegalArgumentException("La urgencia es obligatoria.");
        }
        if (estadoAnterior == null || estadoNuevo == null) {
            throw new IllegalArgumentException("Los estados anterior y nuevo son obligatorios.");
        }
        if (ocurridoEn == null) {
            throw new IllegalArgumentException("La fecha del evento es obligatoria.");
        }
    }
}
