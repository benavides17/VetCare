package com.vetcare.aplicacion.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReprogramarCitaRequest(
        @NotNull @Future LocalDateTime fechaHora) {
}
