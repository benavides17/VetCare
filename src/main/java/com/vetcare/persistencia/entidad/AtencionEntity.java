package com.vetcare.persistencia.entidad;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "atenciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_atencion", discriminatorType = DiscriminatorType.STRING, length = 20)
public abstract class AtencionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false, length = 255)
    private String motivo;

    @Column(length = 1000)
    private String sintomas;

    @Column(length = 1000)
    private String diagnostico;

    @ElementCollection
    @CollectionTable(name = "atencion_procedimientos", joinColumns = @JoinColumn(name = "atencion_id"))
    @Column(name = "procedimiento", nullable = false, length = 255)
    private List<String> procedimientos = new ArrayList<>();

    @OneToMany(mappedBy = "atencion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TratamientoEntity> tratamientos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "historial_medico_id", nullable = false)
    private HistorialMedicoEntity historialMedico;

    protected AtencionEntity() {
    }

    protected AtencionEntity(LocalDateTime fechaHora, String motivo, String sintomas, HistorialMedicoEntity historialMedico) {
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.sintomas = sintomas;
        this.historialMedico = historialMedico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public List<String> getProcedimientos() {
        return procedimientos;
    }

    public void setProcedimientos(List<String> procedimientos) {
        this.procedimientos = procedimientos;
    }

    public List<TratamientoEntity> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<TratamientoEntity> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public HistorialMedicoEntity getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedicoEntity historialMedico) {
        this.historialMedico = historialMedico;
    }
}
