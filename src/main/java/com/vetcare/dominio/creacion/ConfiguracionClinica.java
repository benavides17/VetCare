package com.vetcare.dominio.creacion;

import com.vetcare.dominio.enumeraciones.Prioridad;

/**
 * Singleton thread-safe para centralizar configuración clínica.
 */
public final class ConfiguracionClinica {

    private static volatile ConfiguracionClinica instancia;

    private ConfiguracionClinica() {
    }

    public static ConfiguracionClinica getInstancia() {
        if (instancia == null) {
            synchronized (ConfiguracionClinica.class) {
                if (instancia == null) {
                    instancia = new ConfiguracionClinica();
                }
            }
        }
        return instancia;
    }

    public int minutosMaximosEspera(Prioridad prioridad) {
        if (prioridad == null) {
            throw new IllegalArgumentException("La prioridad es obligatoria.");
        }
        return switch (prioridad) {
            case CRITICA -> 10;
            case ALTA -> 20;
            case MEDIA -> 45;
            case BAJA -> 90;
        };
    }

    public int duracionCitaMinutos() {
        return 30;
    }
}
