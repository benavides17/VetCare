package com.vetcare.dominio.observer;

import com.vetcare.dominio.valores.EventoUrgencia;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publicador del evento de estado de urgencia usando Spring Events.
 */
@Component
public class UrgenciaEventPublisher implements PublicadorEventoUrgencia {

    private final ApplicationEventPublisher publisher;

    public UrgenciaEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publicar(EventoUrgencia evento) {
        publisher.publishEvent(evento);
    }
}
