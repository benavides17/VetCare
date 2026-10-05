/**
 * Se lanza cuando un recurso solicitado no existe en la base de datos.
 */
package com.vetcare.exception;

public class RecursoNoEncontradoException extends VetCareException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
