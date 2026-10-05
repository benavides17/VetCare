package com.vetcare.persistencia.entidad;

import com.vetcare.dominio.enumeraciones.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mascotas")
public class MascotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String especie;

    @Column(length = 100)
    private String raza;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Sexo sexo;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(length = 80)
    private String color;

    @Column(columnDefinition = "NUMERIC(6,2)")
    private Double peso;

    @Column(length = 500)
    private String observaciones;

    @Column(nullable = false)
    private boolean activa = true;

    @ManyToMany(mappedBy = "mascotas")
    private List<PropietarioEntity> propietarios = new ArrayList<>();

    @OneToOne(mappedBy = "mascota", fetch = FetchType.LAZY)
    private HistorialMedicoEntity historialMedico;

    public MascotaEntity() {
    }

    public MascotaEntity(String nombre, String especie, String raza, Sexo sexo,
                        LocalDate fechaNacimiento, String color, Double peso, String observaciones) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.color = color;
        this.peso = peso;
        this.observaciones = observaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public List<PropietarioEntity> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(List<PropietarioEntity> propietarios) {
        this.propietarios = propietarios;
    }

    public HistorialMedicoEntity getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedicoEntity historialMedico) {
        this.historialMedico = historialMedico;
    }
}
