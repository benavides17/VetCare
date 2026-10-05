package com.vetcare.aplicacion.servicio;

import com.vetcare.aplicacion.dto.HistorialMedicoDto;
import java.util.Optional;
import java.time.LocalDate;

public interface HistorialMedicoService {

    Optional<HistorialMedicoDto> buscarPorMascotaId(Long mascotaId);

    Optional<HistorialMedicoDto> buscarPorMascotaId(Long mascotaId, String tipo,
            LocalDate desde, LocalDate hasta, Long veterinarioId, String diagnostico);
}
