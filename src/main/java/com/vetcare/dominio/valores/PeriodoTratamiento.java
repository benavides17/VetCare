/**
 * Representa el intervalo de duración de un tratamiento.
 */
package com.vetcare.dominio.valores;

import java.time.LocalDate;

public record PeriodoTratamiento(LocalDate inicio, LocalDate fin) {

    public PeriodoTratamiento {
        if (inicio == null) {
            throw new IllegalArgumentException("La fecha de inicio es obligatoria.");
        }
        if (fin == null) {
            throw new IllegalArgumentException("La fecha de fin es obligatoria.");
        }
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio.");
        }
    }
}
