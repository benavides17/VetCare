package com.vetcare.dominio.enumeraciones;

/**
 * Prioridad de una urgencia con un nivel numérico para ordenar atención.
 */
public enum Prioridad {
    CRITICA(1),
    ALTA(2),
    MEDIA(3),
    BAJA(4);

    private final int nivel;

    Prioridad(int nivel) {
        this.nivel = nivel;
    }

    public int nivel() {
        return nivel;
    }
}
