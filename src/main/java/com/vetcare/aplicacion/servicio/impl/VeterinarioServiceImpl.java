package com.vetcare.aplicacion.servicio.impl;

import com.vetcare.aplicacion.dto.VeterinarioDto;
import com.vetcare.aplicacion.mapper.VeterinarioMapper;
import com.vetcare.aplicacion.servicio.VeterinarioService;
import com.vetcare.persistencia.entidad.VeterinarioEntity;
import com.vetcare.persistencia.repositorio.VeterinarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository repositorio;

    public VeterinarioServiceImpl(VeterinarioRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<VeterinarioDto> listar() {
        return repositorio.findAll().stream()
                .map(VeterinarioMapper::toDto)
                .toList();
    }

    @Override
    public Optional<VeterinarioDto> buscarPorId(Long id) {
        return repositorio.findById(id)
                .map(VeterinarioMapper::toDto);
    }

    @Override
    public Optional<VeterinarioDto> buscarPorDocumento(String numeroDocumento) {
        return repositorio.findByNumeroDocumento(numeroDocumento)
                .map(VeterinarioMapper::toDto);
    }

    @Override
    public VeterinarioDto guardar(VeterinarioDto dto) {
        VeterinarioEntity entidad = VeterinarioMapper.toEntity(dto);
        return VeterinarioMapper.toDto(repositorio.save(entidad));
    }

    @Override
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}
