package com.vetcare.dominio.strategy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vetcare.dominio.enumeraciones.TipoDocumento;
import com.vetcare.dominio.modelo.Especialidad;
import com.vetcare.dominio.modelo.Veterinario;
import com.vetcare.dominio.valores.Contacto;
import com.vetcare.dominio.valores.Documento;
import com.vetcare.dominio.valores.NombreCompleto;
import com.vetcare.dominio.valores.ResultadoRegla;
import java.util.List;
import org.junit.jupiter.api.Test;

class EvaluadorReglasAsignacionImplTest {

    private final Especialidad especialidad = new Especialidad(20L, "Urgencias", "Atención urgente", true);
    private final Veterinario veterinario = new Veterinario(40L,
            new NombreCompleto("Ana", "García"),
            new Documento(TipoDocumento.CC, "123456"),
            new Contacto("3001234567", "ana@example.com", "Calle 1"),
            "LIC-40", especialidad);

    @Test
    void permiteAgregarUnaReglaSinCambiarElEvaluador() {
        ReglaAsignacion reglaAdicional = (candidatos, requerida) ->
                new ResultadoRegla(true, "Regla adicional satisfecha.");
        EvaluadorReglasAsignacionImpl evaluador =
                new EvaluadorReglasAsignacionImpl(List.of(reglaAdicional));

        assertTrue(evaluador.cumpleReglas(List.of(veterinario), especialidad));
    }

    @Test
    void rechazaLosCandidatosCuandoUnaReglaNoSeCumple() {
        ReglaAsignacion reglaRechazada = (candidatos, requerida) ->
                new ResultadoRegla(false, "Regla adicional no satisfecha.");
        EvaluadorReglasAsignacionImpl evaluador =
                new EvaluadorReglasAsignacionImpl(List.of(reglaRechazada));

        assertFalse(evaluador.cumpleReglas(List.of(veterinario), especialidad));
    }
}
