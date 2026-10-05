package com.vetcare.aplicacion.servicio.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vetcare.aplicacion.dto.CitaDto;
import com.vetcare.dominio.enumeraciones.DisponibilidadVeterinario;
import com.vetcare.dominio.enumeraciones.EstadoCita;
import com.vetcare.exception.ConflictoHorarioException;
import com.vetcare.persistencia.entidad.CitaEntity;
import com.vetcare.persistencia.entidad.MascotaEntity;
import com.vetcare.persistencia.entidad.VeterinarioEntity;
import com.vetcare.persistencia.repositorio.CitaRepository;
import com.vetcare.persistencia.repositorio.MascotaRepository;
import com.vetcare.persistencia.repositorio.VeterinarioRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CitaServiceImplTest {

    @Mock
    private CitaRepository citaRepository;
    @Mock
    private MascotaRepository mascotaRepository;
    @Mock
    private VeterinarioRepository veterinarioRepository;
    @InjectMocks
    private CitaServiceImpl servicio;

    @Test
    void agendaCitaProgramadaCuandoNoHayConflicto() {
        MascotaEntity mascota = mascota();
        VeterinarioEntity veterinario = veterinario();
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);
        CitaDto solicitud = new CitaDto(null, fecha, "Control", null,
                1L, null, 2L, null);

        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(veterinarioRepository.findById(2L)).thenReturn(Optional.of(veterinario));
        when(citaRepository.existeConflictoHorario(anyLong(), any(), any(), any()))
                .thenReturn(false);
        when(citaRepository.save(any(CitaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CitaDto creada = servicio.guardar(solicitud);

        assertEquals("PROGRAMADA", creada.estado());
        assertEquals(fecha, creada.fechaHora());
        verify(citaRepository).save(any(CitaEntity.class));
    }

    @Test
    void noAgendaCitaCuandoVeterinarioTieneConflicto() {
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota()));
        when(veterinarioRepository.findById(2L)).thenReturn(Optional.of(veterinario()));
        when(citaRepository.existeConflictoHorario(anyLong(), any(), any(), any()))
                .thenReturn(true);

        CitaDto solicitud = new CitaDto(null, LocalDateTime.now().plusDays(1),
                "Control", null, 1L, null, 2L, null);

        assertThrows(ConflictoHorarioException.class, () -> servicio.guardar(solicitud));
    }

    @Test
    void cancelarCitaCambiaSuEstado() {
        CitaEntity cita = new CitaEntity();
        cita.setId(8L);
        cita.setFechaHora(LocalDateTime.now().plusDays(1));
        cita.setMotivo("Control");
        cita.setEstado(EstadoCita.PROGRAMADA);
        cita.setMascota(mascota());
        cita.setVeterinario(veterinario());
        when(citaRepository.findById(8L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(any(CitaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CitaDto cancelada = servicio.cancelar(8L);

        assertEquals(EstadoCita.CANCELADA.name(), cancelada.estado());
    }

    private MascotaEntity mascota() {
        MascotaEntity mascota = new MascotaEntity("Luna", "Perro", "Criollo",
                com.vetcare.dominio.enumeraciones.Sexo.HEMBRA,
                LocalDate.of(2020, 1, 1), "Marrón", 12.0, null);
        mascota.setId(1L);
        return mascota;
    }

    private VeterinarioEntity veterinario() {
        VeterinarioEntity veterinario = new VeterinarioEntity();
        veterinario.setId(2L);
        veterinario.setDisponibilidad(DisponibilidadVeterinario.DISPONIBLE);
        veterinario.setActivo(true);
        return veterinario;
    }
}
