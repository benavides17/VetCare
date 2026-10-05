package com.vetcare.dominio.strategy;

import com.vetcare.dominio.modelo.Veterinario;
import com.vetcare.dominio.modelo.Especialidad;
import com.vetcare.dominio.valores.ResultadoRegla;
import java.util.List;

/**
 * Contrato extensible de estrategias (ISP/OCP): Spring recoge nuevas reglas automáticamente.
 */
public interface ReglaAsignacion {

    ResultadoRegla evaluar(List<Veterinario> veterinarios, Especialidad especialidadRequerida);
}
