/**
 * Se lanza cuando un estado intenta avanzar a una transición no permitida.
 */
package com.vetcare.exception;

public class TransicionEstadoInvalidaException extends VetCareException {

    public TransicionEstadoInvalidaException(String mensaje) {
        super(mensaje);
    }
}
