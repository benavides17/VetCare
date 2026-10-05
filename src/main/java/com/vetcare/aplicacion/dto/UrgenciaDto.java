package com.vetcare.aplicacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

public record UrgenciaDto(Long id, @NotNull @PastOrPresent LocalDateTime fechaHora,
                         @NotBlank String motivo, @NotBlank String sintomas,
                         String diagnostico, @NotNull @PastOrPresent LocalDateTime horaLlegada,
                         @NotBlank String prioridad,
                         String estado, Long veterinarioAsignadoId, String destinoRemision,
                         @NotNull Long mascotaId, @NotNull Long especialidadRequeridaId) {

    public UrgenciaDto(Long id, LocalDateTime fechaHora, String motivo, String sintomas,
                       String diagnostico, LocalDateTime horaLlegada, String prioridad,
                       String estado, Long veterinarioAsignadoId, String destinoRemision,
                       Long mascotaId) {
        this(id, fechaHora, motivo, sintomas, diagnostico, horaLlegada, prioridad, estado,
                veterinarioAsignadoId, destinoRemision, mascotaId, null);
    }
}
