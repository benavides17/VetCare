package com.vetcare.aplicacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

public record ConsultaDto(Long id, @NotNull @PastOrPresent LocalDateTime fechaHora,
                         @NotBlank String motivo, String sintomas,
                         String diagnostico, Long citaId, String observaciones, Long mascotaId) {

    public ConsultaDto(Long id, LocalDateTime fechaHora, String motivo, String sintomas,
                       String diagnostico, Long citaId, String observaciones) {
        this(id, fechaHora, motivo, sintomas, diagnostico, citaId, observaciones, null);
    }
}
