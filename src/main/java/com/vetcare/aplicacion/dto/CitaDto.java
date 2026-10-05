package com.vetcare.aplicacion.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CitaDto(Long id, @NotNull @Future LocalDateTime fechaHora,
                      @NotBlank String motivo, String estado,
                      @NotNull Long mascotaId, String mascotaNombre, @NotNull Long veterinarioId,
                      String veterinarioNombre) {
}
