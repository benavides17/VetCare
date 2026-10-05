package com.vetcare.aplicacion.mapper;

import com.vetcare.aplicacion.dto.ConsultaDto;
import com.vetcare.persistencia.entidad.ConsultaEntity;

public final class ConsultaMapper {

    private ConsultaMapper() {
    }

    public static ConsultaDto toDto(ConsultaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ConsultaDto(
                entity.getId(),
                entity.getFechaHora(),
                entity.getMotivo(),
                entity.getSintomas(),
                entity.getDiagnostico(),
                entity.getCita() != null ? entity.getCita().getId() : null,
                entity.getObservaciones(),
                entity.getHistorialMedico() != null && entity.getHistorialMedico().getMascota() != null
                        ? entity.getHistorialMedico().getMascota().getId() : null
        );
    }

    public static ConsultaEntity toEntity(ConsultaDto dto) {
        if (dto == null) {
            return null;
        }
        ConsultaEntity entity = new ConsultaEntity();
        entity.setId(dto.id());
        entity.setFechaHora(dto.fechaHora());
        entity.setMotivo(dto.motivo());
        entity.setSintomas(dto.sintomas());
        entity.setDiagnostico(dto.diagnostico());
        entity.setObservaciones(dto.observaciones());
        return entity;
    }
}
