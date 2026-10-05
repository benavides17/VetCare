package com.vetcare.dominio.modelo;

import com.vetcare.dominio.enumeraciones.Sexo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Représentación del paciente de la clínica.
 */
public class Mascota {

    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private Sexo sexo;
    private LocalDate fechaNacimiento;
    private String color;
    private Double peso;
    private String observaciones;
    private boolean activa;
    private final List<Propietario> propietarios = new ArrayList<>();
    private HistorialMedico historialMedico;

    public Mascota(Long id, String nombre, String especie, String raza, Sexo sexo,
                   LocalDate fechaNacimiento, String color, Double peso, String observaciones) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.color = color;
        this.peso = peso;
        this.observaciones = observaciones;
        this.activa = true;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaza() {
        return raza;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getColor() {
        return color;
    }

    public Double getPeso() {
        return peso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public List<Propietario> getPropietarios() {
        return Collections.unmodifiableList(propietarios);
    }

    public void agregarPropietario(Propietario propietario) {
        if (propietario == null) {
            throw new IllegalArgumentException("El propietario es obligatorio.");
        }
        if (!propietarios.contains(propietario)) {
            propietarios.add(propietario);
        }
    }

    public void removerPropietario(Propietario propietario) {
        if (propietario == null) {
            throw new IllegalArgumentException("El propietario es obligatorio.");
        }
        propietarios.remove(propietario);
    }

    public boolean perteneceA(Propietario propietario) {
        return propietarios.contains(propietario);
    }

    public int edad() {
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }
}
