package com.vetcare.dominio.modelo;

import com.vetcare.dominio.enumeraciones.EstadoTratamiento;
import com.vetcare.dominio.valores.Dosis;
import com.vetcare.dominio.valores.PeriodoTratamiento;

/**
 * Tratamiento prescrito como parte de la atención clínica.
 */
public class Tratamiento {

    private Long id;
    private String medicamento;
    private Dosis dosis;
    private PeriodoTratamiento periodo;
    private String indicaciones;
    private EstadoTratamiento estado;

    public Tratamiento(Long id, String medicamento, Dosis dosis, PeriodoTratamiento periodo, String indicaciones) {
        this.id = id;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.periodo = periodo;
        this.indicaciones = indicaciones;
        this.estado = EstadoTratamiento.ACTIVO;
    }

    public Long getId() {
        return id;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public Dosis getDosis() {
        return dosis;
    }

    public PeriodoTratamiento getPeriodo() {
        return periodo;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public EstadoTratamiento getEstado() {
        return estado;
    }

    public void cambiarEstado(EstadoTratamiento nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado del tratamiento no puede ser nulo.");
        }
        this.estado = nuevoEstado;
    }

    public void finalizar() {
        cambiarEstado(EstadoTratamiento.FINALIZADO);
    }

    public void suspender() {
        cambiarEstado(EstadoTratamiento.SUSPENDIDO);
    }
}
