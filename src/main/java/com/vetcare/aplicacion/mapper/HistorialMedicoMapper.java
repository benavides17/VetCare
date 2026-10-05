package com.vetcare.aplicacion.mapper;

import com.vetcare.aplicacion.dto.AtencionResumenDto;
import com.vetcare.aplicacion.dto.HistorialMedicoDto;
import com.vetcare.persistencia.entidad.AtencionEntity;
import com.vetcare.persistencia.entidad.HistorialMedicoEntity;
import java.util.List;

public final class HistorialMedicoMapper {

    private HistorialMedicoMapper() {
    }

    public static HistorialMedicoDto toDto(HistorialMedicoEntity entity) {
        if (entity == null) {
            return null;
        }
        return toDto(entity, entity.getAtenciones());
    }

    public static HistorialMedicoDto toDto(HistorialMedicoEntity entity,
                                           List<AtencionEntity> atencionesFiltradas) {
        if (entity == null) {
            return null;
        }
        List<AtencionResumenDto> atenciones = atencionesFiltradas == null ? List.of()
                : atencionesFiltradas.stream().map(HistorialMedicoMapper::toResumen).toList();

        return new HistorialMedicoDto(
                entity.getId(),
                entity.getMascota() != null ? entity.getMascota().getId() : null,
                entity.getMascota() != null ? entity.getMascota().getNombre() : null,
                atenciones
        );
    }

    private static AtencionResumenDto toResumen(AtencionEntity atencion) {
        if (atencion == null) {
            return null;
        }
        return new AtencionResumenDto(
                atencion.getId(),
                atencion.getClass().getSimpleName().replace("Entity", ""),
                atencion.getFechaHora(),
                atencion.getMotivo(),
                atencion.getDiagnostico()
        );
    }
}
