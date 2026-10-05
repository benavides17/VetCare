package com.vetcare.aplicacion.mapper;

import com.vetcare.aplicacion.dto.MascotaDto;
import com.vetcare.dominio.enumeraciones.Sexo;
import com.vetcare.persistencia.entidad.MascotaEntity;

public final class MascotaMapper {

    private MascotaMapper() {
    }

    public static MascotaDto toDto(MascotaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new MascotaDto(
                entity.getId(),
                entity.getNombre(),
                entity.getEspecie(),
                entity.getRaza(),
                entity.getSexo() != null ? entity.getSexo().name() : null,
                entity.getFechaNacimiento(),
                entity.getColor(),
                entity.getPeso(),
                entity.getObservaciones(),
                entity.isActiva()
        );
    }

    public static MascotaEntity toEntity(MascotaDto dto) {
        if (dto == null) {
            return null;
        }
        MascotaEntity entity = new MascotaEntity();
        entity.setId(dto.id());
        entity.setNombre(dto.nombre());
        entity.setEspecie(dto.especie());
        entity.setRaza(dto.raza());
        entity.setSexo(dto.sexo() != null ? Sexo.valueOf(dto.sexo()) : null);
        entity.setFechaNacimiento(dto.fechaNacimiento());
        entity.setColor(dto.color());
        entity.setPeso(dto.peso());
        entity.setObservaciones(dto.observaciones());
        entity.setActiva(dto.activa());
        return entity;
    }
}
