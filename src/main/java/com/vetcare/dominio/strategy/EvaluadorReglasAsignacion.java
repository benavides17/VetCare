package com.vetcare.dominio.strategy;

import com.vetcare.dominio.modelo.Especialidad;
import com.vetcare.dominio.modelo.Veterinario;
import java.util.List;

public interface EvaluadorReglasAsignacion {

    boolean cumpleReglas(List<Veterinario> candidatos, Especialidad requerida);
}
