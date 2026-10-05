package com.vetcare.aplicacion.servicio.impl;

import com.vetcare.aplicacion.dto.PropietarioDto;
import com.vetcare.aplicacion.mapper.PropietarioMapper;
import com.vetcare.aplicacion.servicio.PropietarioService;
import com.vetcare.exception.MascotaNoPerteneceAPropietarioException;
import com.vetcare.exception.RecursoNoEncontradoException;
import com.vetcare.persistencia.entidad.MascotaEntity;
import com.vetcare.persistencia.entidad.PropietarioEntity;
import com.vetcare.persistencia.repositorio.MascotaRepository;
import com.vetcare.persistencia.repositorio.PropietarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropietarioServiceImpl implements PropietarioService {

    private final PropietarioRepository repositorio;
    private final MascotaRepository mascotaRepository;

    public PropietarioServiceImpl(PropietarioRepository repositorio, MascotaRepository mascotaRepository) {
        this.repositorio = repositorio;
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public List<PropietarioDto> listar() {
        return repositorio.findAll().stream()
                .map(PropietarioMapper::toDto)
                .toList();
    }

    @Override
    public Optional<PropietarioDto> buscarPorId(Long id) {
        return repositorio.findById(id)
                .map(PropietarioMapper::toDto);
    }

    @Override
    public Optional<PropietarioDto> buscarPorDocumento(String numeroDocumento) {
        return repositorio.findByNumeroDocumento(numeroDocumento)
                .map(PropietarioMapper::toDto);
    }

    @Override
    public PropietarioDto guardar(PropietarioDto dto) {
        PropietarioEntity entidad = PropietarioMapper.toEntity(dto);
        return PropietarioMapper.toDto(repositorio.save(entidad));
    }

    @Override
    @Transactional
    public void asociarMascota(Long propietarioId, Long mascotaId) {
        PropietarioEntity propietario = repositorio.findById(propietarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el propietario."));
        MascotaEntity mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la mascota."));
        propietario.agregarMascota(mascota);
        repositorio.save(propietario);
    }

    @Override
    @Transactional
    public void desasociarMascota(Long propietarioId, Long mascotaId) {
        PropietarioEntity propietario = repositorio.findById(propietarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el propietario."));
        MascotaEntity mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la mascota."));
        if (!propietario.getMascotas().contains(mascota)) {
            throw new MascotaNoPerteneceAPropietarioException(
                    "La mascota no está asociada a este propietario.");
        }
        propietario.quitarMascota(mascota);
        repositorio.save(propietario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        PropietarioEntity propietario = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el propietario."));
        repositorio.delete(propietario);
    }
}
