package com.vetcare.aplicacion.servicio.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vetcare.aplicacion.dto.ConsultaDto;
import com.vetcare.dominio.enumeraciones.EstadoCita;
import com.vetcare.dominio.enumeraciones.Sexo;
import com.vetcare.persistencia.entidad.CitaEntity;
import com.vetcare.persistencia.entidad.ConsultaEntity;
import com.vetcare.persistencia.entidad.HistorialMedicoEntity;
import com.vetcare.persistencia.entidad.MascotaEntity;
import com.vetcare.persistencia.entidad.VeterinarioEntity;
import com.vetcare.persistencia.repositorio.CitaRepository;
import com.vetcare.persistencia.repositorio.ConsultaRepository;
import com.vetcare.persistencia.repositorio.HistorialMedicoRepository;
import com.vetcare.persistencia.repositorio.MascotaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceImplTest {

    @Mock
    private ConsultaRepository consultaRepository;
    @Mock
    private CitaRepository citaRepository;
    @Mock
    private HistorialMedicoRepository historialRepository;
    @Mock
    private MascotaRepository mascotaRepository;
    @InjectMocks
    private ConsultaServiceImpl servicio;

    @Test
    void registraConsultaEnHistorialYMarcaLaCitaComoAtendida() {
        MascotaEntity mascota = new MascotaEntity("Luna", "Perro", "Mestizo",
                Sexo.HEMBRA, LocalDate.of(2020, 1, 1), "Marrón", 12.0, null);
        mascota.setId(1L);
        CitaEntity cita = new CitaEntity();
        cita.setId(2L);
        cita.setFechaHora(LocalDateTime.now().minusMinutes(30));
        cita.setMotivo("Control");
        cita.setMascota(mascota);
        cita.setVeterinario(new VeterinarioEntity());
        cita.setEstado(EstadoCita.PROGRAMADA);
        HistorialMedicoEntity historial = new HistorialMedicoEntity(mascota);
        historial.setId(3L);

        when(citaRepository.findById(2L)).thenReturn(Optional.of(cita));
        when(historialRepository.findByMascotaId(1L)).thenReturn(Optional.empty());
        when(historialRepository.save(any(HistorialMedicoEntity.class))).thenReturn(historial);
        when(consultaRepository.save(any(ConsultaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ConsultaDto solicitud = new ConsultaDto(null, LocalDateTime.now().minusMinutes(20),
                "Control general", "Revisión", "Saludable", 2L, "Sin novedades");
        servicio.guardar(solicitud);

        assertEquals(EstadoCita.ATENDIDA, cita.getEstado());
        ArgumentCaptor<ConsultaEntity> consulta = ArgumentCaptor.forClass(ConsultaEntity.class);
        verify(consultaRepository).save(consulta.capture());
        assertEquals(historial, consulta.getValue().getHistorialMedico());
        verify(historialRepository).save(any(HistorialMedicoEntity.class));
    }
}
