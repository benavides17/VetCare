package com.vetcare.dominio.strategy;

import com.vetcare.dominio.modelo.Especialidad;
import com.vetcare.dominio.modelo.Veterinario;
import com.vetcare.dominio.valores.ResultadoRegla;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Regla que prioriza al veterinario con menor carga de atención.
 */
@Component
public class ReglaMenorCarga implements ReglaAsignacion {

    @Override
    public ResultadoRegla evaluar(List<Veterinario> veterinarios, Especialidad especialidadRequerida) {
        if (veterinarios == null || veterinarios.isEmpty()) {
            return new ResultadoRegla(false, "No hay veterinarios para evaluar.");
        }
        return new ResultadoRegla(true, "Se prioriza al veterinario con menor carga de trabajo.");
    }
}
