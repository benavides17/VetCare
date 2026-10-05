package com.vetcare.aplicacion.servicio.impl;

import com.vetcare.aplicacion.dto.UrgenciaDto;
import com.vetcare.dominio.enumeraciones.DisponibilidadVeterinario;
import com.vetcare.dominio.enumeraciones.EstadoUrgencia;
import com.vetcare.dominio.enumeraciones.Prioridad;
import com.vetcare.dominio.modelo.Especialidad;
import com.vetcare.dominio.modelo.Mascota;
import com.vetcare.dominio.modelo.Urgencia;
import com.vetcare.dominio.modelo.Veterinario;
import com.vetcare.dominio.observer.PublicadorEventoUrgencia;
import com.vetcare.dominio.strategy.EvaluadorReglasAsignacion;
import com.vetcare.dominio.valores.Contacto;
import com.vetcare.dominio.valores.Documento;
import com.vetcare.dominio.valores.EventoUrgencia;
import com.vetcare.dominio.valores.NombreCompleto;
import com.vetcare.exception.RecursoNoEncontradoException;
import com.vetcare.exception.ReglaNegocioException;
import com.vetcare.exception.TransicionEstadoInvalidaException;
import com.vetcare.exception.VeterinarioNoDisponibleException;
import com.vetcare.persistencia.entidad.EspecialidadEntity;
import com.vetcare.persistencia.entidad.HistorialMedicoEntity;
import com.vetcare.persistencia.entidad.MascotaEntity;
import com.vetcare.persistencia.entidad.UrgenciaEntity;
import com.vetcare.persistencia.entidad.VeterinarioEntity;
import com.vetcare.persistencia.repositorio.HistorialMedicoRepository;
import com.vetcare.persistencia.repositorio.EspecialidadRepository;
import com.vetcare.persistencia.repositorio.MascotaRepository;
import com.vetcare.persistencia.repositorio.UrgenciaRepository;
import com.vetcare.persistencia.repositorio.VeterinarioRepository;
import java.util.List;
import java.util.Optional;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UrgenciaServiceImpl implements com.vetcare.aplicacion.servicio.UrgenciaService {

    private final UrgenciaRepository urgenciaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final HistorialMedicoRepository historialRepository;
    private final EspecialidadRepository especialidadRepository;
    private final PublicadorEventoUrgencia eventPublisher;
    private final EvaluadorReglasAsignacion evaluadorReglas;

    public UrgenciaServiceImpl(UrgenciaRepository urgenciaRepository,
                              MascotaRepository mascotaRepository,
                              VeterinarioRepository veterinarioRepository,
                              HistorialMedicoRepository historialRepository,
                              EspecialidadRepository especialidadRepository,
                              PublicadorEventoUrgencia eventPublisher,
                              EvaluadorReglasAsignacion evaluadorReglas) {
        this.urgenciaRepository = urgenciaRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
        this.historialRepository = historialRepository;
        this.especialidadRepository = especialidadRepository;
        this.eventPublisher = eventPublisher;
        this.evaluadorReglas = evaluadorReglas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UrgenciaDto> listar() {
        return urgenciaRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UrgenciaDto> buscarPorId(Long id) {
        return urgenciaRepository.findById(id)
                .map(this::toDto);
    }

    @Override
    @Transactional
    public UrgenciaDto guardar(UrgenciaDto dto) {
        MascotaEntity mascota = mascotaRepository.findById(dto.mascotaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la mascota."));
        EspecialidadEntity especialidad = especialidadRepository.findById(dto.especialidadRequeridaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la especialidad requerida."));
        if (!especialidad.isActiva()) {
            throw new ReglaNegocioException("La especialidad requerida está inactiva.");
        }

        HistorialMedicoEntity historial = historialRepository.findByMascotaId(dto.mascotaId())
                .orElseGet(() -> {
                    HistorialMedicoEntity nuevo = new HistorialMedicoEntity(mascota);
                    return historialRepository.save(nuevo);
                });

        UrgenciaEntity entidad = new UrgenciaEntity(dto.fechaHora(), dto.motivo(), dto.sintomas(),
                dto.horaLlegada(), Prioridad.valueOf(dto.prioridad()), mascota, historial);
        entidad.setDiagnostico(dto.diagnostico());
        entidad.setEspecialidadRequeridaId(especialidad.getId());
        entidad = urgenciaRepository.saveAndFlush(entidad);

        Urgencia urgencia = toDomain(entidad);
        EstadoUrgencia estadoAnterior = urgencia.getEstado();
        VeterinarioEntity veterinario = seleccionarVeterinario(especialidad);
        if (veterinario == null) {
            urgencia.cambiarEstado(EstadoUrgencia.EN_ESPERA);
        } else {
            Veterinario veterinarioDominio = toDomain(veterinario);
            urgencia.asignarVeterinario(veterinarioDominio);
            veterinario.setDisponibilidad(DisponibilidadVeterinario.OCUPADO);
            entidad.setVeterinarioAsignado(veterinario);
        }
        entidad.setEstado(urgencia.getEstado());
        entidad = urgenciaRepository.save(entidad);
        publicarCambio(entidad, estadoAnterior, urgencia.getEstado());

        return toDto(entidad);
    }

    @Override
    @Transactional
    public UrgenciaDto reasignarVeterinario(Long urgenciaId, Long veterinarioId) {
        UrgenciaEntity entidad = urgenciaRepository.findById(urgenciaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la urgencia."));
        Urgencia urgencia = toDomain(entidad);
        if (!urgencia.esActiva()) {
            throw new TransicionEstadoInvalidaException(
                    "No se puede reasignar un veterinario a una urgencia cerrada.");
        }
        EstadoUrgencia estadoAnterior = urgencia.getEstado();
        VeterinarioEntity nuevo = veterinarioRepository.findById(veterinarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el veterinario."));
        if (!nuevo.isActivo()
                || nuevo.getDisponibilidad() != DisponibilidadVeterinario.DISPONIBLE
                || !nuevo.getEspecialidad().getId().equals(entidad.getEspecialidadRequeridaId())) {
            throw new VeterinarioNoDisponibleException(
                    "El veterinario debe estar activo, disponible y tener la especialidad requerida.");
        }
        Veterinario antiguo = urgencia.getVeterinarioAsignado();
        urgencia.reasignarVeterinario(toDomain(nuevo));
        if (antiguo != null) {
            veterinarioRepository.findById(antiguo.getId()).ifPresent(v ->
                    v.setDisponibilidad(DisponibilidadVeterinario.DISPONIBLE));
        }
        nuevo.setDisponibilidad(DisponibilidadVeterinario.OCUPADO);
        entidad.setVeterinarioAsignado(nuevo);
        entidad.setEstado(urgencia.getEstado());
        entidad = urgenciaRepository.save(entidad);
        if (estadoAnterior != urgencia.getEstado()) {
            publicarCambio(entidad, estadoAnterior, urgencia.getEstado());
        }
        return toDto(entidad);
    }

    @Override
    @Transactional
    public UrgenciaDto cambiarEstado(Long id, EstadoUrgencia nuevoEstado, String destinoRemision) {
        if (nuevoEstado == null) {
            throw new ReglaNegocioException("El estado nuevo es obligatorio.");
        }
        UrgenciaEntity entidad = urgenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la urgencia."));
        Urgencia urgencia = toDomain(entidad);
        EstadoUrgencia estadoAnterior = urgencia.getEstado();

        if (nuevoEstado == EstadoUrgencia.EN_ATENCION && entidad.getVeterinarioAsignado() == null) {
            EspecialidadEntity especialidad = especialidadRepository.findById(entidad.getEspecialidadRequeridaId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No se encontró la especialidad requerida para la urgencia."));
            VeterinarioEntity veterinario = seleccionarVeterinario(especialidad);
            if (veterinario == null) {
                throw new VeterinarioNoDisponibleException(
                        "No hay veterinarios disponibles con la especialidad requerida.");
            }
            urgencia.asignarVeterinario(toDomain(veterinario));
            veterinario.setDisponibilidad(DisponibilidadVeterinario.OCUPADO);
            entidad.setVeterinarioAsignado(veterinario);
        } else if (nuevoEstado == EstadoUrgencia.REMITIDA) {
            urgencia.remitir(destinoRemision);
            entidad.setDestinoRemision(destinoRemision);
        } else {
            urgencia.cambiarEstado(nuevoEstado);
        }

        entidad.setEstado(urgencia.getEstado());
        if (!urgencia.esActiva() && entidad.getVeterinarioAsignado() != null) {
            entidad.getVeterinarioAsignado().setDisponibilidad(DisponibilidadVeterinario.DISPONIBLE);
        }
        entidad = urgenciaRepository.save(entidad);
        publicarCambio(entidad, estadoAnterior, urgencia.getEstado());
        return toDto(entidad);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        UrgenciaEntity entidad = urgenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la urgencia."));
        urgenciaRepository.delete(entidad);
    }

    private UrgenciaDto toDto(UrgenciaEntity entity) {
        return new UrgenciaDto(
                entity.getId(),
                entity.getFechaHora(),
                entity.getMotivo(),
                entity.getSintomas(),
                entity.getDiagnostico(),
                entity.getHoraLlegada(),
                entity.getPrioridad() != null ? entity.getPrioridad().name() : null,
                entity.getEstado() != null ? entity.getEstado().name() : null,
                entity.getVeterinarioAsignado() != null ? entity.getVeterinarioAsignado().getId() : null,
                entity.getDestinoRemision(),
                entity.getMascota() != null ? entity.getMascota().getId() : null,
                entity.getEspecialidadRequeridaId()
        );
    }

    private VeterinarioEntity seleccionarVeterinario(EspecialidadEntity especialidad) {
        Especialidad especialidadDominio = new Especialidad(especialidad.getId(),
                especialidad.getNombre(), especialidad.getDescripcion(), especialidad.isActiva());
        List<VeterinarioEntity> candidatos = veterinarioRepository
                .findByEspecialidad_IdAndActivoTrueAndDisponibilidad(
                        especialidad.getId(), DisponibilidadVeterinario.DISPONIBLE);
        List<Veterinario> candidatosDominio = candidatos.stream().map(this::toDomain).toList();
        if (!evaluadorReglas.cumpleReglas(candidatosDominio, especialidadDominio)) {
            return null;
        }
        return candidatos.stream()
                .min(Comparator.comparingLong(v -> urgenciaRepository.countByVeterinarioAsignadoIdAndEstadoIn(
                        v.getId(), List.of(EstadoUrgencia.EN_ATENCION, EstadoUrgencia.ESTABILIZADA))))
                .orElse(null);
    }

    private Urgencia toDomain(UrgenciaEntity entity) {
        Veterinario veterinario = entity.getVeterinarioAsignado() == null
                ? null : toDomain(entity.getVeterinarioAsignado());
        return new Urgencia(entity.getId(), entity.getFechaHora(), entity.getMotivo(),
                entity.getSintomas(), entity.getHoraLlegada(), entity.getPrioridad(),
                toDomain(entity.getMascota()), entity.getEstado(), veterinario);
    }

    private Mascota toDomain(MascotaEntity entity) {
        return new Mascota(entity.getId(), entity.getNombre(), entity.getEspecie(), entity.getRaza(),
                entity.getSexo(), entity.getFechaNacimiento(), entity.getColor(),
                entity.getPeso(), entity.getObservaciones());
    }

    private Veterinario toDomain(VeterinarioEntity entity) {
        EspecialidadEntity especialidadEntity = entity.getEspecialidad();
        Especialidad especialidad = new Especialidad(especialidadEntity.getId(),
                especialidadEntity.getNombre(), especialidadEntity.getDescripcion(),
                especialidadEntity.isActiva());
        String telefono = Objects.requireNonNullElse(entity.getTelefono(), "No registrado");
        String correo = Objects.requireNonNullElse(entity.getEmail(), "sin-correo@vetcare.local");
        return new Veterinario(entity.getId(),
                new NombreCompleto(entity.getNombre(), entity.getApellidos()),
                new Documento(entity.getTipoDocumento(), entity.getNumeroDocumento()),
                new Contacto(telefono, correo, "No registrada en el sistema"),
                entity.getLicencia(), especialidad);
    }

    private void publicarCambio(UrgenciaEntity entity, EstadoUrgencia anterior, EstadoUrgencia nuevo) {
        if (anterior == nuevo) {
            return;
        }
        Long veterinarioId = entity.getVeterinarioAsignado() == null
                ? null : entity.getVeterinarioAsignado().getId();
        eventPublisher.publicar(new EventoUrgencia(entity.getId(), anterior, nuevo,
                veterinarioId, OffsetDateTime.now()));
    }
}
