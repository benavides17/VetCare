package com.vetcare.aplicacion.servicio.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vetcare.aplicacion.dto.UrgenciaDto;
import com.vetcare.dominio.enumeraciones.DisponibilidadVeterinario;
import com.vetcare.dominio.enumeraciones.EstadoUrgencia;
import com.vetcare.dominio.enumeraciones.Sexo;
import com.vetcare.dominio.enumeraciones.TipoDocumento;
import com.vetcare.dominio.observer.PublicadorEventoUrgencia;
import com.vetcare.dominio.strategy.EvaluadorReglasAsignacion;
import com.vetcare.dominio.valores.EventoUrgencia;
import com.vetcare.persistencia.entidad.EspecialidadEntity;
import com.vetcare.persistencia.entidad.HistorialMedicoEntity;
import com.vetcare.persistencia.entidad.MascotaEntity;
import com.vetcare.persistencia.entidad.UrgenciaEntity;
import com.vetcare.persistencia.entidad.VeterinarioEntity;
import com.vetcare.persistencia.repositorio.EspecialidadRepository;
import com.vetcare.persistencia.repositorio.HistorialMedicoRepository;
import com.vetcare.persistencia.repositorio.MascotaRepository;
import com.vetcare.persistencia.repositorio.UrgenciaRepository;
import com.vetcare.persistencia.repositorio.VeterinarioRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UrgenciaServiceImplTest {

    @Mock
    private UrgenciaRepository urgenciaRepository;
    @Mock
    private MascotaRepository mascotaRepository;
    @Mock
    private VeterinarioRepository veterinarioRepository;
    @Mock
    private HistorialMedicoRepository historialRepository;
    @Mock
    private EspecialidadRepository especialidadRepository;
    @Mock
    private PublicadorEventoUrgencia eventPublisher;
    @Mock
    private EvaluadorReglasAsignacion evaluadorReglas;
    @InjectMocks
    private UrgenciaServiceImpl servicio;

    @Test
    void registraUrgenciaEnEsperaYPublicaEventoCuandoNoHayVeterinariosDisponibles() {
        MascotaEntity mascota = mascota();
        EspecialidadEntity especialidad = especialidad();
        HistorialMedicoEntity historial = new HistorialMedicoEntity(mascota);

        when(mascotaRepository.findById(10L)).thenReturn(Optional.of(mascota));
        when(especialidadRepository.findById(20L)).thenReturn(Optional.of(especialidad));
        when(historialRepository.findByMascotaId(10L)).thenReturn(Optional.of(historial));
        when(urgenciaRepository.saveAndFlush(any(UrgenciaEntity.class))).thenAnswer(invocation -> {
            UrgenciaEntity entity = invocation.getArgument(0);
            entity.setId(30L);
            return entity;
        });
        when(veterinarioRepository.findByEspecialidad_IdAndActivoTrueAndDisponibilidad(
                20L, DisponibilidadVeterinario.DISPONIBLE)).thenReturn(List.of());
        when(urgenciaRepository.save(any(UrgenciaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UrgenciaDto creada = servicio.guardar(solicitud());

        assertEquals(EstadoUrgencia.EN_ESPERA.name(), creada.estado());
        ArgumentCaptor<EventoUrgencia> evento = ArgumentCaptor.forClass(EventoUrgencia.class);
        verify(eventPublisher).publicar(evento.capture());
        assertEquals(EstadoUrgencia.RECIBIDA, evento.getValue().estadoAnterior());
        assertEquals(EstadoUrgencia.EN_ESPERA, evento.getValue().estadoNuevo());
    }

    @Test
    void asignaVeterinarioEspecialistaYPublicaElCambioAAtencion() {
        MascotaEntity mascota = mascota();
        EspecialidadEntity especialidad = especialidad();
        HistorialMedicoEntity historial = new HistorialMedicoEntity(mascota);
        VeterinarioEntity veterinario = veterinario(especialidad, 40L);
        VeterinarioEntity veterinarioMenorCarga = veterinario(especialidad, 41L);

        when(mascotaRepository.findById(10L)).thenReturn(Optional.of(mascota));
        when(especialidadRepository.findById(20L)).thenReturn(Optional.of(especialidad));
        when(historialRepository.findByMascotaId(10L)).thenReturn(Optional.of(historial));
        when(urgenciaRepository.saveAndFlush(any(UrgenciaEntity.class))).thenAnswer(invocation -> {
            UrgenciaEntity entity = invocation.getArgument(0);
            entity.setId(30L);
            return entity;
        });
        when(veterinarioRepository.findByEspecialidad_IdAndActivoTrueAndDisponibilidad(
                20L, DisponibilidadVeterinario.DISPONIBLE))
                .thenReturn(List.of(veterinario, veterinarioMenorCarga));
        when(evaluadorReglas.cumpleReglas(anyList(), any())).thenReturn(true);
        when(urgenciaRepository.countByVeterinarioAsignadoIdAndEstadoIn(
                40L, List.of(EstadoUrgencia.EN_ATENCION, EstadoUrgencia.ESTABILIZADA))).thenReturn(2L);
        when(urgenciaRepository.countByVeterinarioAsignadoIdAndEstadoIn(
                41L, List.of(EstadoUrgencia.EN_ATENCION, EstadoUrgencia.ESTABILIZADA))).thenReturn(0L);
        when(urgenciaRepository.save(any(UrgenciaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UrgenciaDto creada = servicio.guardar(solicitud());

        assertEquals(EstadoUrgencia.EN_ATENCION.name(), creada.estado());
        assertEquals(41L, creada.veterinarioAsignadoId());
        assertEquals(DisponibilidadVeterinario.DISPONIBLE, veterinario.getDisponibilidad());
        assertEquals(DisponibilidadVeterinario.OCUPADO, veterinarioMenorCarga.getDisponibilidad());
        ArgumentCaptor<EventoUrgencia> evento = ArgumentCaptor.forClass(EventoUrgencia.class);
        verify(eventPublisher).publicar(evento.capture());
        assertEquals(EstadoUrgencia.EN_ATENCION, evento.getValue().estadoNuevo());
    }

    @Test
    void reasignaVeterinarioAUnaUrgenciaEnEsperaYLaPasaAAtencion() {
        MascotaEntity mascota = mascota();
        EspecialidadEntity especialidad = especialidad();
        HistorialMedicoEntity historial = new HistorialMedicoEntity(mascota);
        UrgenciaEntity urgencia = new UrgenciaEntity(LocalDateTime.now(), "Herida",
                "Corte en una pata", LocalDateTime.now(), com.vetcare.dominio.enumeraciones.Prioridad.ALTA,
                mascota, historial);
        urgencia.setId(30L);
        urgencia.setEstado(EstadoUrgencia.EN_ESPERA);
        urgencia.setEspecialidadRequeridaId(especialidad.getId());
        VeterinarioEntity veterinario = veterinario(especialidad, 41L);

        when(urgenciaRepository.findById(30L)).thenReturn(Optional.of(urgencia));
        when(veterinarioRepository.findById(41L)).thenReturn(Optional.of(veterinario));
        when(urgenciaRepository.save(any(UrgenciaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UrgenciaDto actualizada = servicio.reasignarVeterinario(30L, 41L);

        assertEquals(EstadoUrgencia.EN_ATENCION.name(), actualizada.estado());
        assertEquals(41L, actualizada.veterinarioAsignadoId());
        assertEquals(DisponibilidadVeterinario.OCUPADO, veterinario.getDisponibilidad());
        ArgumentCaptor<EventoUrgencia> evento = ArgumentCaptor.forClass(EventoUrgencia.class);
        verify(eventPublisher).publicar(evento.capture());
        assertEquals(EstadoUrgencia.EN_ESPERA, evento.getValue().estadoAnterior());
        assertEquals(EstadoUrgencia.EN_ATENCION, evento.getValue().estadoNuevo());
    }

    private UrgenciaDto solicitud() {
        LocalDateTime ahora = LocalDateTime.now();
        return new UrgenciaDto(null, ahora, "Herida", "Corte en una pata",
                null, ahora, "ALTA", null, null, null, 10L, 20L);
    }

    private MascotaEntity mascota() {
        MascotaEntity mascota = new MascotaEntity("Milo", "Perro", "Mestizo", Sexo.MACHO,
                LocalDate.of(2021, 1, 1), "Negro", 12.0, null);
        mascota.setId(10L);
        return mascota;
    }

    private EspecialidadEntity especialidad() {
        EspecialidadEntity especialidad = new EspecialidadEntity("Urgencias", "Atención urgente", true);
        especialidad.setId(20L);
        return especialidad;
    }

    private VeterinarioEntity veterinario(EspecialidadEntity especialidad, Long id) {
        VeterinarioEntity veterinario = new VeterinarioEntity("Ana", "García",
                TipoDocumento.CC, "123456", "3001234567", "ana@example.com",
                "LIC-40", especialidad);
        veterinario.setId(id);
        return veterinario;
    }
}
