/**
 * Objeto de valor para los datos de contacto de una persona.
 */
package com.vetcare.dominio.valores;

public record Contacto(String telefono, String correo, String direccion) {

    public Contacto {
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }
        if (direccion == null || direccion.isBlank()) {
            throw new IllegalArgumentException("La dirección es obligatoria.");
        }
    }
}
