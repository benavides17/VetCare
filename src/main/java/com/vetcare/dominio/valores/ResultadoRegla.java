/**
 * Resultado de evaluar una regla de asignación del sistema.
 */
package com.vetcare.dominio.valores;

public record ResultadoRegla(boolean cumple, String mensaje) {

    public ResultadoRegla {
        if (mensaje == null || mensaje.isBlank()) {
            throw new IllegalArgumentException("El mensaje de la regla es obligatorio.");
        }
    }
}
