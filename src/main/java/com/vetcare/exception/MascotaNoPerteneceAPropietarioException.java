/**
 * Indica que la mascota no está asociada al propietario que intenta operarla.
 */
package com.vetcare.exception;

public class MascotaNoPerteneceAPropietarioException extends VetCareException {

    public MascotaNoPerteneceAPropietarioException(String mensaje) {
        super(mensaje);
    }
}
