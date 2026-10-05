package com.vetcare.dominio.observer;

import com.vetcare.dominio.valores.EventoUrgencia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener del evento de cambio de estado para notificar de forma asíncrona.
 */
@Component
public class EventoUrgenciaListener {

    private static final Logger log = LoggerFactory.getLogger(EventoUrgenciaListener.class);

    private final NotificadorVeterinario notificadorVeterinario;

    public EventoUrgenciaListener(NotificadorVeterinario notificadorVeterinario) {
        this.notificadorVeterinario = notificadorVeterinario;
    }

    @EventListener
    public void onEventoUrgencia(EventoUrgencia evento) {
        try {
            notificadorVeterinario.notificar(evento);
        } catch (Exception ex) {
            log.warn("El envío de la notificación de urgencia falló para urgencia {}. Se registra el error y se continúa.",
                    evento.urgenciaId(), ex);
        }
    }
}
