package com.vetcare.aplicacion.mapper;

import com.vetcare.aplicacion.dto.CitaDto;
import com.vetcare.dominio.enumeraciones.EstadoCita;
import com.vetcare.persistencia.entidad.CitaEntity;

public final class CitaMapper {

    private CitaMapper() {
    }

    public static CitaDto toDto(CitaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CitaDto(
                entity.getId(),
                entity.getFechaHora(),
                entity.getMotivo(),
                entity.getEstado() != null ? entity.getEstado().name() : null,
                entity.getMascota() != null ? entity.getMascota().getId() : null,
                entity.getMascota() != null ? entity.getMascota().getNombre() : null,
                entity.getVeterinario() != null ? entity.getVeterinario().getId() : null,
                entity.getVeterinario() != null ? entity.getVeterinario().getNombre() + " " + entity.getVeterinario().getApellidos() : null
        );
    }

    public static CitaEntity toEntity(CitaDto dto) {
        if (dto == null) {
            return null;
        }
        CitaEntity entity = new CitaEntity();
        entity.setId(dto.id());
        entity.setFechaHora(dto.fechaHora());
        entity.setMotivo(dto.motivo());
        entity.setEstado(EstadoCita.PROGRAMADA);
        return entity;
    }
}
