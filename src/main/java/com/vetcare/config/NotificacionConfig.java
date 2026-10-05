package com.vetcare.config;

import com.vetcare.dominio.observer.NotificadorVeterinario;
import com.vetcare.dominio.observer.ServicioMensajeriaVeterinario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de dependencias para notificaciones de urgencias.
 */
@Configuration
public class NotificacionConfig {

    @Bean
    public NotificadorVeterinario notificadorVeterinario() {
        return new ServicioMensajeriaVeterinario();
    }
}
