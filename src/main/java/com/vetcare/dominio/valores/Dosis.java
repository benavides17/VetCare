/**
 * Valor que modela la dosis de un tratamiento con su unidad y frecuencia.
 */
package com.vetcare.dominio.valores;

import java.math.BigDecimal;

public record Dosis(BigDecimal cantidad, String unidad, String frecuencia) {

    public Dosis {
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (unidad == null || unidad.isBlank()) {
            throw new IllegalArgumentException("La unidad es obligatoria.");
        }
        if (frecuencia == null || frecuencia.isBlank()) {
            throw new IllegalArgumentException("La frecuencia es obligatoria.");
        }
    }
}
