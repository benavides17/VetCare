package com.vetcare.dominio.strategy;

import com.vetcare.dominio.modelo.Especialidad;
import com.vetcare.dominio.modelo.Veterinario;
import com.vetcare.dominio.valores.ResultadoRegla;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Regla que exige disponibilidad del veterinario para atender una urgencia.
 */
@Component
public class ReglaDisponible implements ReglaAsignacion {

    @Override
    public ResultadoRegla evaluar(List<Veterinario> veterinarios, Especialidad especialidadRequerida) {
        var disponibles = veterinarios == null ? List.<Veterinario>of() : veterinarios.stream()
                .filter(Veterinario::estaDisponible)
                .toList();
        return new ResultadoRegla(!disponibles.isEmpty(),
                disponibles.isEmpty() ? "No hay veterinarios disponibles." : "Hay veterinarios disponibles.");
    }
}
