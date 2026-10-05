/**
 * Excepción para fallos de validación de reglas de negocio no cubiertas por otros casos.
 */
package com.vetcare.exception;

public class ReglaNegocioException extends VetCareException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
