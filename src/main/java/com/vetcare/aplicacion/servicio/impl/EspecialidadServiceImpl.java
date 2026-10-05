package com.vetcare.aplicacion.servicio.impl;

import com.vetcare.aplicacion.dto.EspecialidadDto;
import com.vetcare.aplicacion.mapper.EspecialidadMapper;
import com.vetcare.aplicacion.servicio.EspecialidadService;
import com.vetcare.persistencia.entidad.EspecialidadEntity;
import com.vetcare.persistencia.repositorio.EspecialidadRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository repositorio;

    public EspecialidadServiceImpl(EspecialidadRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<EspecialidadDto> listar() {
        return repositorio.findAll().stream()
                .map(EspecialidadMapper::toDto)
                .toList();
    }

    @Override
    public Optional<EspecialidadDto> buscarPorId(Long id) {
        return repositorio.findById(id)
                .map(EspecialidadMapper::toDto);
    }

    @Override
    public EspecialidadDto guardar(EspecialidadDto dto) {
        EspecialidadEntity entidad = EspecialidadMapper.toEntity(dto);
        return EspecialidadMapper.toDto(repositorio.save(entidad));
    }

    @Override
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}
