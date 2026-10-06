package com.vetcare.dominio.observer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;

import com.vetcare.dominio.enumeraciones.EstadoUrgencia;
import com.vetcare.dominio.valores.EventoUrgencia;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventoUrgenciaListenerTest {

    @Mock
    private NotificadorVeterinario notificador;
    @InjectMocks
    private EventoUrgenciaListener listener;

    @Test
    void falloDeNotificacionNoPropagaNiInterrumpeElFlujoClinico() {
        EventoUrgencia evento = new EventoUrgencia(1L, EstadoUrgencia.RECIBIDA,
                EstadoUrgencia.EN_ESPERA, null, OffsetDateTime.now());
        doThrow(new IllegalStateException("Servicio externo no disponible"))
                .when(notificador).notificar(evento);

        assertDoesNotThrow(() -> listener.onEventoUrgencia(evento));
    }
}
