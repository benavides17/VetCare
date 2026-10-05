package com.vetcare.aplicacion.mapper;

import com.vetcare.aplicacion.dto.VeterinarioDto;
import com.vetcare.dominio.enumeraciones.DisponibilidadVeterinario;
import com.vetcare.dominio.enumeraciones.TipoDocumento;
import com.vetcare.persistencia.entidad.VeterinarioEntity;

public final class VeterinarioMapper {

    private VeterinarioMapper() {
    }

    public static VeterinarioDto toDto(VeterinarioEntity entity) {
        if (entity == null) {
            return null;
        }
        return new VeterinarioDto(
                entity.getId(),
                entity.getNombre(),
                entity.getApellidos(),
                entity.getTipoDocumento() != null ? entity.getTipoDocumento().name() : null,
                entity.getNumeroDocumento(),
                entity.getTelefono(),
                entity.getEmail(),
                entity.getLicencia(),
                entity.getDisponibilidad() != null ? entity.getDisponibilidad().name() : null,
                entity.isActivo(),
                entity.getEspecialidad() != null ? entity.getEspecialidad().getId() : null,
                entity.getEspecialidad() != null ? entity.getEspecialidad().getNombre() : null
        );
    }

    public static VeterinarioEntity toEntity(VeterinarioDto dto) {
        if (dto == null) {
            return null;
        }
        VeterinarioEntity entity = new VeterinarioEntity();
        entity.setId(dto.id());
        entity.setNombre(dto.nombre());
        entity.setApellidos(dto.apellidos());
        entity.setTipoDocumento(dto.tipoDocumento() != null ? TipoDocumento.valueOf(dto.tipoDocumento()) : null);
        entity.setNumeroDocumento(dto.numeroDocumento());
        entity.setTelefono(dto.telefono());
        entity.setEmail(dto.email());
        entity.setLicencia(dto.licencia());
        entity.setDisponibilidad(dto.disponibilidad() != null ? DisponibilidadVeterinario.valueOf(dto.disponibilidad()) : null);
        entity.setActivo(dto.activo());
        return entity;
    }
}
