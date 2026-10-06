package com.vetcare.aplicacion.servicio.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.vetcare.persistencia.entidad.ConsultaEntity;
import com.vetcare.persistencia.entidad.HistorialMedicoEntity;
import com.vetcare.persistencia.entidad.MascotaEntity;
import com.vetcare.persistencia.entidad.UrgenciaEntity;
import com.vetcare.persistencia.repositorio.HistorialMedicoRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HistorialMedicoServiceImplTest {

    @Mock
    private HistorialMedicoRepository repository;
    @InjectMocks
    private HistorialMedicoServiceImpl servicio;

    @Test
    void filtraHistorialPorTipoFechaYDiagnostico() {
        MascotaEntity mascota = new MascotaEntity();
        mascota.setId(5L);
        mascota.setNombre("Nina");
        HistorialMedicoEntity historial = new HistorialMedicoEntity(mascota);
        historial.setId(7L);

        ConsultaEntity consulta = new ConsultaEntity();
        consulta.setId(10L);
        consulta.setFechaHora(LocalDateTime.of(2026, 2, 2, 9, 0));
        consulta.setMotivo("Control");
        consulta.setDiagnostico("Dermatitis leve");
        consulta.setHistorialMedico(historial);

        UrgenciaEntity urgencia = new UrgenciaEntity();
        urgencia.setId(11L);
        urgencia.setFechaHora(LocalDateTime.of(2026, 2, 3, 9, 0));
        urgencia.setMotivo("Herida");
        urgencia.setDiagnostico("Corte superficial");
        urgencia.setHistorialMedico(historial);

        historial.getAtenciones().add(consulta);
        historial.getAtenciones().add(urgencia);
        when(repository.findByMascotaId(5L)).thenReturn(Optional.of(historial));

        var filtrado = servicio.buscarPorMascotaId(5L, "consulta",
                LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 2), null, "dermatitis")
                .orElseThrow();

        assertEquals(1, filtrado.atenciones().size());
        assertEquals(10L, filtrado.atenciones().get(0).id());
        assertEquals("Consulta", filtrado.atenciones().get(0).tipo());
    }

    @Test
    void rechazaUnRangoDeFechasInvertido() {
        assertEquals("La fecha final no puede ser anterior a la inicial.",
                assertThrows(IllegalArgumentException.class, () -> servicio.buscarPorMascotaId(5L,
                        null, LocalDate.of(2026, 3, 2), LocalDate.of(2026, 3, 1), null, null))
                        .getMessage());
    }
}
