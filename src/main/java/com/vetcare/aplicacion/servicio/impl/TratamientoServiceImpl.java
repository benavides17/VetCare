package com.vetcare.aplicacion.servicio.impl;

import com.vetcare.aplicacion.dto.TratamientoDto;
import com.vetcare.aplicacion.mapper.TratamientoMapper;
import com.vetcare.aplicacion.servicio.TratamientoService;
import com.vetcare.dominio.enumeraciones.EstadoTratamiento;
import com.vetcare.dominio.modelo.Tratamiento;
import com.vetcare.dominio.valores.Dosis;
import com.vetcare.dominio.valores.PeriodoTratamiento;
import com.vetcare.exception.RecursoNoEncontradoException;
import com.vetcare.persistencia.entidad.AtencionEntity;
import com.vetcare.persistencia.entidad.TratamientoEntity;
import com.vetcare.persistencia.repositorio.AtencionRepository;
import com.vetcare.persistencia.repositorio.TratamientoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TratamientoServiceImpl implements TratamientoService {

    private final TratamientoRepository tratamientoRepository;
    private final AtencionRepository atencionRepository;

    public TratamientoServiceImpl(TratamientoRepository tratamientoRepository,
                                 AtencionRepository atencionRepository) {
        this.tratamientoRepository = tratamientoRepository;
        this.atencionRepository = atencionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TratamientoDto> listar() {
        return tratamientoRepository.findAll().stream()
                .map(TratamientoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TratamientoDto> buscarPorId(Long id) {
        return tratamientoRepository.findById(id)
                .map(TratamientoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TratamientoDto> buscarPorAtencionId(Long atencionId) {
        return tratamientoRepository.findByAtencionId(atencionId).stream()
                .map(TratamientoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public TratamientoDto guardar(TratamientoDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos del tratamiento son obligatorios");
        }
        Tratamiento tratamiento = new Tratamiento(dto.id(), dto.medicamento(),
                new Dosis(dto.dosisCantidad(), dto.unidad(), dto.frecuencia()),
                new PeriodoTratamiento(dto.fechaInicio(), dto.fechaFin()), dto.indicaciones());
        if (dto.estado() != null) {
            tratamiento.cambiarEstado(EstadoTratamiento.valueOf(dto.estado()));
        }

        TratamientoEntity entidad = TratamientoMapper.toEntity(dto);
        entidad.setEstado(tratamiento.getEstado());
        AtencionEntity atencion = atencionRepository.findById(dto.atencionId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la atención."));
        entidad.setAtencion(atencion);

        return TratamientoMapper.toDto(tratamientoRepository.save(entidad));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        TratamientoEntity tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el tratamiento."));
        tratamientoRepository.delete(tratamiento);
    }
}
