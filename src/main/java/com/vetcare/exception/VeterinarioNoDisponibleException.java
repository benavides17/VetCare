/**
 * Se lanza cuando no hay veterinarios con capacidad para atender una urgencia.
 */
package com.vetcare.exception;

public class VeterinarioNoDisponibleException extends VetCareException {

    public VeterinarioNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
