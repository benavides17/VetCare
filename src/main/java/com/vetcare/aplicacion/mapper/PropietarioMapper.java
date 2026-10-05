package com.vetcare.aplicacion.mapper;

import com.vetcare.aplicacion.dto.PropietarioDto;
import com.vetcare.persistencia.entidad.PropietarioEntity;

public final class PropietarioMapper {

    private PropietarioMapper() {
    }

    public static PropietarioDto toDto(PropietarioEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PropietarioDto(
                entity.getId(),
                entity.getNombre(),
                entity.getApellidos(),
                entity.getTipoDocumento() != null ? entity.getTipoDocumento().name() : null,
                entity.getNumeroDocumento(),
                entity.getTelefono(),
                entity.getEmail(),
                entity.isActivo()
        );
    }

    public static PropietarioEntity toEntity(PropietarioDto dto) {
        if (dto == null) {
            return null;
        }
        PropietarioEntity entity = new PropietarioEntity();
        entity.setId(dto.id());
        entity.setNombre(dto.nombre());
        entity.setApellidos(dto.apellidos());
        entity.setTipoDocumento(dto.tipoDocumento() != null ? com.vetcare.dominio.enumeraciones.TipoDocumento.valueOf(dto.tipoDocumento()) : null);
        entity.setNumeroDocumento(dto.numeroDocumento());
        entity.setTelefono(dto.telefono());
        entity.setEmail(dto.email());
        entity.setActivo(dto.activo());
        return entity;
    }
}
