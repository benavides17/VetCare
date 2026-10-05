package com.vetcare.aplicacion.mapper;

import com.vetcare.aplicacion.dto.EspecialidadDto;
import com.vetcare.persistencia.entidad.EspecialidadEntity;

public final class EspecialidadMapper {

    private EspecialidadMapper() {
    }

    public static EspecialidadDto toDto(EspecialidadEntity entity) {
        if (entity == null) {
            return null;
        }
        return new EspecialidadDto(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.isActiva()
        );
    }

    public static EspecialidadEntity toEntity(EspecialidadDto dto) {
        if (dto == null) {
            return null;
        }
        EspecialidadEntity entity = new EspecialidadEntity();
        entity.setId(dto.id());
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setActiva(dto.activa());
        return entity;
    }
}
