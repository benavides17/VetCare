package com.vetcare.aplicacion.servicio.impl;

import com.vetcare.aplicacion.dto.ConsultaDto;
import com.vetcare.aplicacion.mapper.ConsultaMapper;
import com.vetcare.aplicacion.servicio.ConsultaService;
import com.vetcare.dominio.enumeraciones.EstadoCita;
import com.vetcare.exception.RecursoNoEncontradoException;
import com.vetcare.exception.ReglaNegocioException;
import com.vetcare.persistencia.entidad.CitaEntity;
import com.vetcare.persistencia.entidad.ConsultaEntity;
import com.vetcare.persistencia.entidad.HistorialMedicoEntity;
import com.vetcare.persistencia.entidad.MascotaEntity;
import com.vetcare.persistencia.repositorio.CitaRepository;
import com.vetcare.persistencia.repositorio.ConsultaRepository;
import com.vetcare.persistencia.repositorio.HistorialMedicoRepository;
import com.vetcare.persistencia.repositorio.MascotaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final CitaRepository citaRepository;
    private final HistorialMedicoRepository historialRepository;
    private final MascotaRepository mascotaRepository;

    public ConsultaServiceImpl(ConsultaRepository consultaRepository,
                              CitaRepository citaRepository,
                              HistorialMedicoRepository historialRepository,
                              MascotaRepository mascotaRepository) {
        this.consultaRepository = consultaRepository;
        this.citaRepository = citaRepository;
        this.historialRepository = historialRepository;
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultaDto> listar() {
        return consultaRepository.findAll().stream()
                .map(ConsultaMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConsultaDto> buscarPorId(Long id) {
        return consultaRepository.findById(id)
                .map(ConsultaMapper::toDto);
    }

    @Override
    @Transactional
    public ConsultaDto guardar(ConsultaDto dto) {
        ConsultaEntity entidad = ConsultaMapper.toEntity(dto);
        CitaEntity cita = null;

        if (dto.citaId() != null) {
            cita = citaRepository.findById(dto.citaId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la cita."));
            if (cita.getEstado() != EstadoCita.PROGRAMADA) {
                throw new ReglaNegocioException("Solo se puede registrar una consulta para una cita programada.");
            }
            entidad.setCita(cita);
        }

        MascotaEntity mascota;
        if (cita != null) {
            mascota = cita.getMascota();
            if (dto.mascotaId() != null && !dto.mascotaId().equals(mascota.getId())) {
                throw new ReglaNegocioException("La mascota indicada no corresponde a la cita.");
            }
        } else {
            if (dto.mascotaId() == null) {
                throw new ReglaNegocioException(
                        "Una consulta sin cita debe indicar la mascota asociada.");
            }
            mascota = mascotaRepository.findById(dto.mascotaId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la mascota."));
        }
        HistorialMedicoEntity historial = historialRepository.findByMascotaId(mascota.getId())
                .orElseGet(() -> historialRepository.save(new HistorialMedicoEntity(mascota)));
        entidad.setHistorialMedico(historial);

        if (dto.id() != null) {
            Optional<ConsultaEntity> existente = consultaRepository.findById(dto.id());
            if (existente.isPresent()) {
                entidad.setHistorialMedico(existente.get().getHistorialMedico());
                entidad.setId(existente.get().getId());
            }
        }

        if (cita != null) {
            cita.setEstado(EstadoCita.ATENDIDA);
        }

        return ConsultaMapper.toDto(consultaRepository.save(entidad));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!consultaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró la consulta.");
        }
        consultaRepository.deleteById(id);
    }
}
