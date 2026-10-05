/**
 * Se usa cuando se intenta registrar un propietario ya existente.
 */
package com.vetcare.exception;

public class PropietarioDuplicadoException extends VetCareException {

    public PropietarioDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
