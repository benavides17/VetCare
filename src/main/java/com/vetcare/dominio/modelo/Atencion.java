package com.vetcare.dominio.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Atención médica base que puede ser Consulta o Urgencia.
 *
 * Principio SOLID: Liskov y polimorfismo.
 */
public abstract class Atencion {

    private Long id;
    private LocalDateTime fechaHora;
    private String motivo;
    private String sintomas;
    private String diagnostico;
    private final List<String> procedimientos = new ArrayList<>();
    private final List<Tratamiento> tratamientos = new ArrayList<>();

    protected Atencion(Long id, LocalDateTime fechaHora, String motivo, String sintomas) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.sintomas = sintomas;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getSintomas() {
        return sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public List<String> getProcedimientos() {
        return Collections.unmodifiableList(procedimientos);
    }

    public List<Tratamiento> getTratamientos() {
        return Collections.unmodifiableList(tratamientos);
    }

    public void registrarDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void agregarTratamiento(Tratamiento tratamiento) {
        if (tratamiento == null) {
            throw new IllegalArgumentException("El tratamiento no puede ser nulo.");
        }
        this.tratamientos.add(tratamiento);
    }

    public void agregarProcedimiento(String procedimiento) {
        if (procedimiento == null || procedimiento.isBlank()) {
            throw new IllegalArgumentException("El procedimiento es obligatorio.");
        }
        this.procedimientos.add(procedimiento);
    }

    public abstract String tipo();
}
