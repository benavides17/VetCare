package com.vetcare.aplicacion.mapper;

import com.vetcare.aplicacion.dto.TratamientoDto;
import com.vetcare.dominio.enumeraciones.EstadoTratamiento;
import com.vetcare.persistencia.entidad.TratamientoEntity;

public final class TratamientoMapper {

    private TratamientoMapper() {
    }

    public static TratamientoDto toDto(TratamientoEntity entity) {
        if (entity == null) {
            return null;
        }

        return new TratamientoDto(
                entity.getId(),
                entity.getMedicamento(),
                entity.getDosisCantidad(),
                entity.getUnidad(),
                entity.getFrecuencia(),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getIndicaciones(),
                entity.getEstado() != null ? entity.getEstado().name() : null,
                entity.getAtencion() != null ? entity.getAtencion().getId() : null
        );
    }

    public static TratamientoEntity toEntity(TratamientoDto dto) {
        if (dto == null) {
            return null;
        }

        TratamientoEntity entity = new TratamientoEntity();
        entity.setId(dto.id());
        entity.setMedicamento(dto.medicamento());
        entity.setDosisCantidad(dto.dosisCantidad());
        entity.setUnidad(dto.unidad());
        entity.setFrecuencia(dto.frecuencia());
        entity.setFechaInicio(dto.fechaInicio());
        entity.setFechaFin(dto.fechaFin());
        entity.setIndicaciones(dto.indicaciones());
        entity.setEstado(dto.estado() != null ? EstadoTratamiento.valueOf(dto.estado()) : EstadoTratamiento.ACTIVO);
        return entity;
    }
}
