package com.vetcare.dominio.creacion;

import com.vetcare.dominio.enumeraciones.Prioridad;
import com.vetcare.dominio.modelo.Mascota;
import java.time.LocalDateTime;

/**
 * Requisición de atención de urgencia para crear la entidad del dominio.
 */
public record SolicitudUrgencia(Long id,
                               LocalDateTime fechaHora,
                               String motivo,
                               String sintomas,
                               LocalDateTime horaLlegada,
                               Prioridad prioridad,
                               Mascota mascota) {
}
