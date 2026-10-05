package com.vetcare.dominio.observer;

import com.vetcare.dominio.valores.EventoUrgencia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementación concreta del servicio externo de mensajes.
 */
public class ServicioMensajeriaVeterinario implements NotificadorVeterinario {

    private static final Logger log = LoggerFactory.getLogger(ServicioMensajeriaVeterinario.class);

    @Override
    public void notificar(EventoUrgencia evento) {
        log.info("Se notifica al veterinario sobre la urgencia {} con cambio {} -> {}.",
                evento.urgenciaId(), evento.estadoAnterior(), evento.estadoNuevo());
    }
}
