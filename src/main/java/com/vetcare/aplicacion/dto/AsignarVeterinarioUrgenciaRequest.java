package com.vetcare.aplicacion.dto;

import jakarta.validation.constraints.NotNull;

public record AsignarVeterinarioUrgenciaRequest(@NotNull Long veterinarioId) {
}
