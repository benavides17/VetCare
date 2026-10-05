package com.vetcare.dominio.observer;

import com.vetcare.dominio.valores.EventoUrgencia;

/**
 * Contrato específico para notificar a un veterinario sobre cambios de urgencia.
 *
 * Principio SOLID: I (Segregación de interfaces).
 */
public interface NotificadorVeterinario {

    void notificar(EventoUrgencia evento);
}
