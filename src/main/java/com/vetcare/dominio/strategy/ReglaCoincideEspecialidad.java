package com.vetcare.dominio.strategy;

import com.vetcare.dominio.modelo.Especialidad;
import com.vetcare.dominio.modelo.Veterinario;
import com.vetcare.dominio.valores.ResultadoRegla;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Regla que valida si el veterinario atiende la especialidad requerida.
 */
@Component
public class ReglaCoincideEspecialidad implements ReglaAsignacion {

    @Override
    public ResultadoRegla evaluar(List<Veterinario> veterinarios, Especialidad especialidadRequerida) {
        if (especialidadRequerida == null) {
            return new ResultadoRegla(false, "La especialidad requerida es obligatoria.");
        }
        var coincidentes = veterinarios == null ? List.<Veterinario>of() : veterinarios.stream()
                .filter(v -> v.atiende(especialidadRequerida))
                .toList();
        return new ResultadoRegla(!coincidentes.isEmpty(),
                coincidentes.isEmpty() ? "No existe veterinario para la especialidad requerida." : "Coincide especialidad.");
    }
}
