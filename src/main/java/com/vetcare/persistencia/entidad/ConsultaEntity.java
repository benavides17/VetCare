package com.vetcare.persistencia.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CONSULTA")
public class ConsultaEntity extends AtencionEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id")
    private CitaEntity cita;

    @Column(length = 1000)
    private String observaciones;

    public ConsultaEntity() {
        super();
    }

    public ConsultaEntity(LocalDateTime fechaHora, String motivo, String sintomas,
                          CitaEntity cita, HistorialMedicoEntity historialMedico) {
        super(fechaHora, motivo, sintomas, historialMedico);
        this.cita = cita;
    }

    public CitaEntity getCita() {
        return cita;
    }

    public void setCita(CitaEntity cita) {
        this.cita = cita;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
