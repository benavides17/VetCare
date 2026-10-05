/**
 * Valor inmutable que encapsula nombre y apellidos.
 */
package com.vetcare.dominio.valores;

public record NombreCompleto(String nombres, String apellidos) {

    public NombreCompleto {
        if (nombres == null || nombres.isBlank()) {
            throw new IllegalArgumentException("Los nombres son obligatorios.");
        }
        if (apellidos == null || apellidos.isBlank()) {
            throw new IllegalArgumentException("Los apellidos son obligatorios.");
        }
    }
}
