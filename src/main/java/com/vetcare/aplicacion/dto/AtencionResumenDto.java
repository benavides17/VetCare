package com.vetcare.aplicacion.dto;

import java.time.LocalDateTime;

public record AtencionResumenDto(Long id, String tipo, LocalDateTime fechaHora, String motivo, String diagnostico) {
}
