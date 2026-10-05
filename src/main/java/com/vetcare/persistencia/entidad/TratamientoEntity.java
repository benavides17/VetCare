package com.vetcare.persistencia.entidad;

import com.vetcare.dominio.enumeraciones.EstadoTratamiento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tratamientos")
public class TratamientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String medicamento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal dosisCantidad;

    @Column(nullable = false, length = 50)
    private String unidad;

    @Column(nullable = false, length = 50)
    private String frecuencia;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(length = 500)
    private String indicaciones;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private EstadoTratamiento estado = EstadoTratamiento.ACTIVO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "atencion_id", nullable = false)
    private AtencionEntity atencion;

    public TratamientoEntity() {
    }

    public TratamientoEntity(String medicamento, BigDecimal dosisCantidad, String unidad,
                            String frecuencia, LocalDate fechaInicio, LocalDate fechaFin,
                            String indicaciones, AtencionEntity atencion) {
        this.medicamento = medicamento;
        this.dosisCantidad = dosisCantidad;
        this.unidad = unidad;
        this.frecuencia = frecuencia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.indicaciones = indicaciones;
        this.atencion = atencion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public BigDecimal getDosisCantidad() {
        return dosisCantidad;
    }

    public void setDosisCantidad(BigDecimal dosisCantidad) {
        this.dosisCantidad = dosisCantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public EstadoTratamiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoTratamiento estado) {
        this.estado = estado;
    }

    public AtencionEntity getAtencion() {
        return atencion;
    }

    public void setAtencion(AtencionEntity atencion) {
        this.atencion = atencion;
    }
}
