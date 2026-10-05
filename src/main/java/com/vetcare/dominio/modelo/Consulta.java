package com.vetcare.dominio.modelo;

import java.time.LocalDateTime;

/**
 * Consulta especializada de Atencion, sustituible por cualquier consumidor de la clase base (LSP).
 */
public class Consulta extends Atencion {

    private Cita cita;
    private String observaciones;

    public Consulta(Long id, LocalDateTime fechaHora, String motivo, String sintomas, Cita cita) {
        super(id, fechaHora, motivo, sintomas);
        this.cita = cita;
    }

    public Cita getCita() {
        return cita;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        if (observaciones == null || observaciones.isBlank()) {
            throw new IllegalArgumentException("Las observaciones no pueden estar vacías.");
        }
        this.observaciones = observaciones;
    }

    public void registrarResultado(String observaciones) {
        setObservaciones(observaciones);
    }

    @Override
    public String tipo() {
        return "CONSULTA";
    }
}
