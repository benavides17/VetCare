package com.vetcare.dominio.strategy;

import com.vetcare.dominio.modelo.Especialidad;
import com.vetcare.dominio.modelo.Veterinario;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EvaluadorReglasAsignacionImpl implements EvaluadorReglasAsignacion {

    private final List<ReglaAsignacion> reglas;

    public EvaluadorReglasAsignacionImpl(List<ReglaAsignacion> reglas) {
        this.reglas = List.copyOf(reglas);
    }

    @Override
    public boolean cumpleReglas(List<Veterinario> candidatos, Especialidad requerida) {
        if (candidatos == null || candidatos.isEmpty()) {
            return false;
        }
        return reglas.stream()
                .allMatch(regla -> regla.evaluar(candidatos, requerida).cumple());
    }
}
