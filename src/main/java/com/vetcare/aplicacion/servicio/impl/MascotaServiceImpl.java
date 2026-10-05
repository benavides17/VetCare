package com.vetcare.aplicacion.servicio.impl;

import com.vetcare.aplicacion.dto.MascotaDto;
import com.vetcare.aplicacion.mapper.MascotaMapper;
import com.vetcare.aplicacion.servicio.MascotaService;
import com.vetcare.persistencia.entidad.MascotaEntity;
import com.vetcare.persistencia.repositorio.MascotaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository repositorio;

    public MascotaServiceImpl(MascotaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<MascotaDto> listar() {
        return repositorio.findAll().stream()
                .map(MascotaMapper::toDto)
                .toList();
    }

    @Override
    public Optional<MascotaDto> buscarPorId(Long id) {
        return repositorio.findById(id)
                .map(MascotaMapper::toDto);
    }

    @Override
    public MascotaDto guardar(MascotaDto dto) {
        MascotaEntity entidad = MascotaMapper.toEntity(dto);
        return MascotaMapper.toDto(repositorio.save(entidad));
    }

    @Override
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}
