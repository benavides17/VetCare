package com.vetcare.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vetcare.dominio.enumeraciones.DisponibilidadVeterinario;
import com.vetcare.dominio.enumeraciones.EstadoUrgencia;
import com.vetcare.dominio.enumeraciones.Prioridad;
import com.vetcare.dominio.enumeraciones.Sexo;
import com.vetcare.dominio.enumeraciones.TipoDocumento;
import com.vetcare.dominio.enumeraciones.EstadoTratamiento;
import com.vetcare.dominio.modelo.Cita;
import com.vetcare.dominio.modelo.Consulta;
import com.vetcare.dominio.modelo.Especialidad;
import com.vetcare.dominio.modelo.HistorialMedico;
import com.vetcare.dominio.modelo.Mascota;
import com.vetcare.dominio.modelo.Tratamiento;
import com.vetcare.dominio.modelo.Urgencia;
import com.vetcare.dominio.modelo.Veterinario;
import com.vetcare.dominio.valores.Contacto;
import com.vetcare.dominio.valores.Documento;
import com.vetcare.dominio.valores.Dosis;
import com.vetcare.dominio.valores.NombreCompleto;
import com.vetcare.dominio.valores.PeriodoTratamiento;
import com.vetcare.exception.TransicionEstadoInvalidaException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DominioVetCareTest {

    @Test
    void debeCrearMascotaYValidarEdad() {
        Mascota mascota = new Mascota(1L, "Luna", "Perro", "Labrador", Sexo.HEMBRA,
                LocalDate.of(2020, 5, 10), "Marrón", 12.5, "Muy activa");

        assertEquals("Luna", mascota.getNombre());
        assertEquals(6, mascota.edad());
        assertTrue(mascota.isActiva());
    }

    @Test
    void debeCrearUrgenciaYTransitarEstados() {
        Mascota mascota = new Mascota(1L, "Max", "Perro", "Pastor", Sexo.MACHO,
                LocalDate.of(2019, 1, 1), "Negro", 20.0, "Fuerte");
        Especialidad especialidad = new Especialidad(1L, "Cirugía", "Especialidad quirúrgica", true);
        Veterinario veterinario = new Veterinario(10L,
                new NombreCompleto("Ana", "García"),
                new Documento(TipoDocumento.DNI, "12345678"),
                new Contacto("555123456", "ana@vetcare.com", "Calle 1"),
                "LIC-001", especialidad);

        Urgencia urgencia = new Urgencia(2L, LocalDateTime.now(), "Dolor abdominal",
                "Vomita y decaimiento", LocalDateTime.now(), Prioridad.ALTA, mascota);

        urgencia.asignarVeterinario(veterinario);
        assertEquals(EstadoUrgencia.EN_ATENCION, urgencia.getEstado());
        assertEquals(veterinario, urgencia.getVeterinarioAsignado());
        assertFalse(veterinario.estaDisponible());

        urgencia.cambiarEstado(EstadoUrgencia.ESTABILIZADA);
        assertEquals(EstadoUrgencia.ESTABILIZADA, urgencia.getEstado());

        urgencia.remitir("Hospital referenciado");
        assertEquals("Hospital referenciado", urgencia.getDestinoRemision());
        assertEquals(EstadoUrgencia.REMITIDA, urgencia.getEstado());
    }

    @Test
    void debeRechazarTransicionInvalidaDeUrgencia() {
        Mascota mascota = new Mascota(1L, "Toby", "Gato", "Siamés", Sexo.MACHO,
                LocalDate.of(2021, 3, 10), "Blanco", 4.2, "Activo");
        Urgencia urgencia = new Urgencia(3L, LocalDateTime.now(), "Herida", "Arañazo",
                LocalDateTime.now(), Prioridad.MEDIA, mascota);

        assertEquals("El nuevo estado no puede ser nulo.",
                assertThrows(IllegalArgumentException.class, () -> urgencia.cambiarEstado(null))
                        .getMessage());
        assertEquals("No se puede cambiar de RECIBIDA a REMITIDA.",
                assertThrows(TransicionEstadoInvalidaException.class,
                        () -> urgencia.cambiarEstado(EstadoUrgencia.REMITIDA)).getMessage());
    }

    @Test
    void debeCrearConsultaYAsignarObservaciones() {
        Especialidad especialidad = new Especialidad(2L, "Dermatología", "Piel y alergias", true);
        Veterinario veterinario = new Veterinario(11L,
                new NombreCompleto("Luis", "Pérez"),
                new Documento(TipoDocumento.CEDULA, "87654321"),
                new Contacto("555987654", "luis@vetcare.com", "Avenida 2"),
                "LIC-002", especialidad);
        Consulta consulta = new Consulta(5L, LocalDateTime.now(), "Control", "Se revisa la piel", null);

        consulta.setObservaciones("Todo dentro de lo esperado");
        assertEquals("Todo dentro de lo esperado", consulta.getObservaciones());
        assertNotNull(consulta.getFechaHora());
        assertEquals("CONSULTA", consulta.tipo());
        assertTrue(veterinario.estaDisponible());
    }

    @Test
    void debeValidarValoresObjetosDeDominio() {
        assertEquals("El número de documento es obligatorio.",
                assertThrows(IllegalArgumentException.class,
                        () -> new Documento(TipoDocumento.DNI, " ")).getMessage());
        assertEquals("El teléfono es obligatorio.",
                assertThrows(IllegalArgumentException.class,
                        () -> new Contacto("", "a@b.com", "Calle")).getMessage());
        assertEquals("Los nombres son obligatorios.",
                assertThrows(IllegalArgumentException.class,
                        () -> new NombreCompleto("", "García")).getMessage());
        assertEquals("La cantidad debe ser mayor que cero.",
                assertThrows(IllegalArgumentException.class,
                        () -> new Dosis(BigDecimal.ZERO, "mg", "cada 8h")).getMessage());
        assertEquals("La fecha de fin no puede ser anterior a la de inicio.",
                assertThrows(IllegalArgumentException.class,
                        () -> new PeriodoTratamiento(LocalDate.now().plusDays(5), LocalDate.now()))
                        .getMessage());
    }

    @Test
    void debeRegistrarHistoricoClinicoEnOrdenCronologico() {
        Mascota mascota = new Mascota(15L, "Nina", "Gato", "Persa", Sexo.HEMBRA,
                LocalDate.of(2022, 6, 2), "Gris", 3.8, "Muy tranquila");
        HistorialMedico historial = new HistorialMedico(1L, mascota);

        Urgencia urgente = new Urgencia(20L, LocalDateTime.of(2024, 10, 1, 9, 15),
                "Dolor", "Vomita", LocalDateTime.of(2024, 10, 1, 9, 20), Prioridad.ALTA, mascota);
        Consulta consulta = new Consulta(21L, LocalDateTime.of(2024, 10, 1, 10, 0),
                "Control", "Revisión general", null);

        historial.registrar(consulta);
        historial.registrar(urgente);

        assertEquals(2, historial.getAtenciones().size());
        assertEquals(urgente, historial.cronologico().get(0));
        assertEquals(consulta, historial.cronologico().get(1));
    }

    @Test
    void debeRespetarTransicionesValidasDeUrgencia() {
        Mascota mascota = new Mascota(16L, "Milo", "Perro", "Boxer", Sexo.MACHO,
                LocalDate.of(2021, 4, 12), "Marrón", 25.5, "Activo");
        Urgencia urgencia = new Urgencia(30L, LocalDateTime.now(), "Hemorragia", "Sangrado leve",
                LocalDateTime.now(), Prioridad.MEDIA, mascota);

        assertEquals("No se puede cambiar de RECIBIDA a FINALIZADA.",
                assertThrows(TransicionEstadoInvalidaException.class, urgencia::finalizar)
                        .getMessage());

        urgencia.cambiarEstado(EstadoUrgencia.EN_ATENCION);
        urgencia.cambiarEstado(EstadoUrgencia.ESTABILIZADA);
        urgencia.remitir("Hospital regional");

        assertEquals(EstadoUrgencia.REMITIDA, urgencia.getEstado());
        assertEquals("Hospital regional", urgencia.getDestinoRemision());
    }

    @Test
    void debeRegistrarTratamientoYActualizarSuEstado() {
        Especialidad especialidad = new Especialidad(4L, "Dermatología", "Piel", true);
        Veterinario veterinario = new Veterinario(15L,
                new NombreCompleto("Rosa", "Díaz"),
                new Documento(TipoDocumento.CC, "1122334455"),
                new Contacto("555111222", "rosa@vetcare.com", "Calle 9"),
                "LIC-004", especialidad);
        Mascota mascota = new Mascota(18L, "Mimi", "Gato", "Bengal", Sexo.HEMBRA,
                LocalDate.of(2023, 1, 10), "Naranja", 4.6, "Tranquila");
        Cita cita = new Cita(30L, LocalDateTime.now().plusDays(1), "Control", mascota, veterinario);
        Consulta consulta = new Consulta(40L, LocalDateTime.now(), "Revisión", "Se observa irritación",
                cita);

        Tratamiento tratamiento = new Tratamiento(50L, "Antihistamínico",
                new Dosis(new BigDecimal("5"), "mg", "cada 12h"),
                new PeriodoTratamiento(LocalDate.now(), LocalDate.now().plusDays(7)),
                "Aplicar según indicación");

        consulta.agregarTratamiento(tratamiento);
        tratamiento.finalizar();

        assertEquals(1, consulta.getTratamientos().size());
        assertEquals(EstadoTratamiento.FINALIZADO, tratamiento.getEstado());
        assertEquals("Las observaciones no pueden estar vacías.",
                assertThrows(IllegalArgumentException.class,
                        () -> consulta.registrarResultado(" ")).getMessage());
    }

    @Test
    void debeMarcarVeterinarioComoOcupado() {
        Especialidad especialidad = new Especialidad(3L, "Cardiología", "Especialidad cardiaca", true);
        Veterinario veterinario = new Veterinario(12L,
                new NombreCompleto("Sara", "López"),
                new Documento(TipoDocumento.PASAPORTE, "AB123456"),
                new Contacto("555654321", "sara@vetcare.com", "Calle 3"),
                "LIC-003", especialidad);

        assertEquals(DisponibilidadVeterinario.DISPONIBLE, veterinario.getDisponibilidad());
        veterinario.ocupar();
        assertEquals(DisponibilidadVeterinario.OCUPADO, veterinario.getDisponibilidad());
        assertFalse(veterinario.estaDisponible());
    }
}
