/**
 * Excepción base del dominio para todas las reglas de negocio de VetCare.
 */
package com.vetcare.exception;

public class VetCareException extends RuntimeException {

    public VetCareException(String message) {
        super(message);
    }

    public VetCareException(String message, Throwable cause) {
        super(message, cause);
    }
}
