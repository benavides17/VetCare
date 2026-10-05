package com.vetcare.dominio.enumeraciones;

/**
 * Estados válidos del ciclo de vida; concentra las transiciones del patrón State.
 */
public enum EstadoUrgencia {
    RECIBIDA,
    EN_ESPERA,
    EN_ATENCION,
    ESTABILIZADA,
    FINALIZADA,
    REMITIDA;

    public boolean esActiva() {
        return this != FINALIZADA && this != REMITIDA;
    }

    public boolean puedePasarA(EstadoUrgencia destino) {
        return switch (this) {
            case RECIBIDA -> destino == EN_ESPERA || destino == EN_ATENCION;
            case EN_ESPERA -> destino == EN_ATENCION || destino == FINALIZADA || destino == REMITIDA;
            case EN_ATENCION -> destino == ESTABILIZADA || destino == FINALIZADA || destino == REMITIDA;
            case ESTABILIZADA -> destino == FINALIZADA || destino == REMITIDA;
            case FINALIZADA, REMITIDA -> false;
        };
    }
}
