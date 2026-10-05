package com.vetcare.aplicacion.servicio.impl;

import com.vetcare.aplicacion.dto.HistorialMedicoDto;
import com.vetcare.aplicacion.mapper.HistorialMedicoMapper;
import com.vetcare.aplicacion.servicio.HistorialMedicoService;
import com.vetcare.persistencia.entidad.AtencionEntity;
import com.vetcare.persistencia.entidad.ConsultaEntity;
import com.vetcare.persistencia.entidad.HistorialMedicoEntity;
import com.vetcare.persistencia.entidad.UrgenciaEntity;
import com.vetcare.persistencia.repositorio.HistorialMedicoRepository;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistorialMedicoServiceImpl implements HistorialMedicoService {

    private final HistorialMedicoRepository repositorio;

    public HistorialMedicoServiceImpl(HistorialMedicoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HistorialMedicoDto> buscarPorMascotaId(Long mascotaId) {
        return repositorio.findByMascotaId(mascotaId)
                .map(HistorialMedicoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HistorialMedicoDto> buscarPorMascotaId(Long mascotaId, String tipo,
            LocalDate desde, LocalDate hasta, Long veterinarioId, String diagnostico) {
        if (desde != null && hasta != null && hasta.isBefore(desde)) {
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la inicial.");
        }
        return repositorio.findByMascotaId(mascotaId).map(historial -> {
            var filtradas = historial.getAtenciones().stream()
                    .filter(atencion -> coincideTipo(atencion, tipo))
                    .filter(atencion -> desde == null
                            || !atencion.getFechaHora().toLocalDate().isBefore(desde))
                    .filter(atencion -> hasta == null
                            || !atencion.getFechaHora().toLocalDate().isAfter(hasta))
                    .filter(atencion -> veterinarioId == null
                            || veterinarioId.equals(obtenerVeterinarioId(atencion)))
                    .filter(atencion -> coincideDiagnostico(atencion, diagnostico))
                    .toList();
            return HistorialMedicoMapper.toDto(historial, filtradas);
        });
    }

    private boolean coincideTipo(AtencionEntity atencion, String tipo) {
        if (tipo == null || tipo.isBlank()) {
            return true;
        }
        String tipoReal = atencion instanceof ConsultaEntity ? "CONSULTA"
                : atencion instanceof UrgenciaEntity ? "URGENCIA" : "";
        return tipoReal.equalsIgnoreCase(tipo.trim());
    }

    private Long obtenerVeterinarioId(AtencionEntity atencion) {
        if (atencion instanceof ConsultaEntity consulta && consulta.getCita() != null
                && consulta.getCita().getVeterinario() != null) {
            return consulta.getCita().getVeterinario().getId();
        }
        if (atencion instanceof UrgenciaEntity urgencia && urgencia.getVeterinarioAsignado() != null) {
            return urgencia.getVeterinarioAsignado().getId();
        }
        return null;
    }

    private boolean coincideDiagnostico(AtencionEntity atencion, String diagnostico) {
        if (diagnostico == null || diagnostico.isBlank()) {
            return true;
        }
        return atencion.getDiagnostico() != null
                && atencion.getDiagnostico().toLowerCase(Locale.ROOT)
                        .contains(diagnostico.trim().toLowerCase(Locale.ROOT));
    }
}
