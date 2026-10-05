package com.vetcare.aplicacion.dto;

import java.util.List;

public record HistorialMedicoDto(Long id, Long mascotaId, String mascotaNombre, List<AtencionResumenDto> atenciones) {
}

