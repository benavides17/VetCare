package com.vetcare.aplicacion.servicio.impl;

import com.vetcare.aplicacion.dto.CitaDto;
import com.vetcare.aplicacion.mapper.CitaMapper;
import com.vetcare.aplicacion.servicio.CitaService;
import com.vetcare.dominio.creacion.ConfiguracionClinica;
import com.vetcare.dominio.enumeraciones.EstadoCita;
import com.vetcare.dominio.modelo.Cita;
import com.vetcare.exception.ReglaNegocioException;
import com.vetcare.dominio.enumeraciones.DisponibilidadVeterinario;
import com.vetcare.persistencia.entidad.CitaEntity;
import com.vetcare.persistencia.entidad.MascotaEntity;
import com.vetcare.persistencia.entidad.VeterinarioEntity;
import com.vetcare.persistencia.repositorio.CitaRepository;
import com.vetcare.persistencia.repositorio.MascotaRepository;
import com.vetcare.persistencia.repositorio.VeterinarioRepository;
import com.vetcare.exception.ConflictoHorarioException;
import com.vetcare.exception.RecursoNoEncontradoException;
import com.vetcare.exception.VeterinarioNoDisponibleException;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/** Servicio de casos de uso; depende de repositorios por sus interfaces (DIP). */
@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    public CitaServiceImpl(CitaRepository citaRepository,
                          MascotaRepository mascotaRepository,
                          VeterinarioRepository veterinarioRepository) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDto> listar() {
        return citaRepository.findAll().stream()
                .map(CitaMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitaDto> buscarPorId(Long id) {
        return citaRepository.findById(id)
                .map(CitaMapper::toDto);
    }

    @Override
    @Transactional
    public CitaDto guardar(CitaDto dto) {
        CitaEntity entidad = CitaMapper.toEntity(dto);

        MascotaEntity mascota = mascotaRepository.findById(dto.mascotaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la mascota."));
        entidad.setMascota(mascota);

        VeterinarioEntity veterinario = veterinarioRepository.findById(dto.veterinarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el veterinario."));
        if (!veterinario.isActivo()
                || veterinario.getDisponibilidad() != DisponibilidadVeterinario.DISPONIBLE) {
            throw new VeterinarioNoDisponibleException("El veterinario no está disponible.");
        }
        entidad.setVeterinario(veterinario);
        entidad.setEstado(EstadoCita.PROGRAMADA);
        validarHorario(veterinario.getId(), entidad.getFechaHora(), null);

        return CitaMapper.toDto(citaRepository.save(entidad));
    }

    @Override
    @Transactional
    public CitaDto reprogramar(Long id, LocalDateTime nuevaFechaHora) {
        CitaEntity entidad = obtenerEntidad(id);
        Cita cita = new Cita(entidad.getId(), entidad.getFechaHora(), entidad.getMotivo(),
                entidad.getEstado(), null, null);
        cita.reprogramar(nuevaFechaHora);
        validarHorario(entidad.getVeterinario().getId(), nuevaFechaHora, id);
        entidad.setFechaHora(cita.getFechaHora());
        return CitaMapper.toDto(citaRepository.save(entidad));
    }

    @Override
    @Transactional
    public CitaDto cancelar(Long id) {
        CitaEntity entidad = obtenerEntidad(id);
        Cita cita = new Cita(entidad.getId(), entidad.getFechaHora(), entidad.getMotivo(),
                entidad.getEstado(), null, null);
        cita.cancelar();
        entidad.setEstado(cita.getEstado());
        return CitaMapper.toDto(citaRepository.save(entidad));
    }

    @Override
    @Transactional
    public CitaDto marcarAtendida(Long id) {
        CitaEntity entidad = obtenerEntidad(id);
        Cita cita = new Cita(entidad.getId(), entidad.getFechaHora(), entidad.getMotivo(),
                entidad.getEstado(), null, null);
        cita.marcarAtendida();
        entidad.setEstado(cita.getEstado());
        return CitaMapper.toDto(citaRepository.save(entidad));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        citaRepository.delete(obtenerEntidad(id));
    }

    private CitaEntity obtenerEntidad(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la cita."));
    }

    private void validarHorario(Long veterinarioId, LocalDateTime fechaHora, Long citaId) {
        if (fechaHora == null || !fechaHora.isAfter(LocalDateTime.now())) {
            throw new ReglaNegocioException("La fecha de la cita debe ser futura.");
        }
        int duracion = ConfiguracionClinica.getInstancia().duracionCitaMinutos();
        LocalDateTime desde = fechaHora.minusMinutes(duracion);
        LocalDateTime hasta = fechaHora.plusMinutes(duracion);
        if (citaRepository.existeConflictoHorario(veterinarioId, desde, hasta, citaId)) {
            throw new ConflictoHorarioException("El veterinario ya tiene una cita en ese horario.");
        }
    }
}
