package com.vetcare.aplicacion.dto;

import com.vetcare.dominio.enumeraciones.EstadoUrgencia;
import jakarta.validation.constraints.NotNull;

public record CambiarEstadoUrgenciaRequest(@NotNull EstadoUrgencia estado, String destinoRemision) {
}
