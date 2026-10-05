/**
 * Encarna un conflicto de disponibilidad o horario de atención.
 */
package com.vetcare.exception;

public class ConflictoHorarioException extends VetCareException {

    public ConflictoHorarioException(String mensaje) {
        super(mensaje);
    }
}
