package com.vetcare.dominio.state;

import com.vetcare.dominio.enumeraciones.EstadoUrgencia;

/**
 * Estado de la urgencia modelado con el patrón State.
 */
public interface EstadoUrgenciaState {

    boolean puedePasarA(EstadoUrgencia destino);

    boolean esActiva();
}
