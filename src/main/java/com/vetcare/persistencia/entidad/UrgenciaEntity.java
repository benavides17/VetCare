package com.vetcare.persistencia.entidad;

import com.vetcare.dominio.enumeraciones.EstadoUrgencia;
import com.vetcare.dominio.enumeraciones.Prioridad;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("URGENCIA")
public class UrgenciaEntity extends AtencionEntity {

    @Column(nullable = false)
    private LocalDateTime horaLlegada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Prioridad prioridad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private EstadoUrgencia estado = EstadoUrgencia.RECIBIDA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinario_asignado_id")
    private VeterinarioEntity veterinarioAsignado;

    @Column(length = 255)
    private String destinoRemision;

    @Column(name = "especialidad_requerida_id")
    private Long especialidadRequeridaId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mascota_id", nullable = false)
    private MascotaEntity mascota;

    public UrgenciaEntity() {
        super();
    }

    public UrgenciaEntity(LocalDateTime fechaHora, String motivo, String sintomas,
                         LocalDateTime horaLlegada, Prioridad prioridad, MascotaEntity mascota,
                         HistorialMedicoEntity historialMedico) {
        super(fechaHora, motivo, sintomas, historialMedico);
        this.horaLlegada = horaLlegada;
        this.prioridad = prioridad;
        this.mascota = mascota;
    }

    public LocalDateTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalDateTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public EstadoUrgencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoUrgencia estado) {
        this.estado = estado;
    }

    public VeterinarioEntity getVeterinarioAsignado() {
        return veterinarioAsignado;
    }

    public void setVeterinarioAsignado(VeterinarioEntity veterinarioAsignado) {
        this.veterinarioAsignado = veterinarioAsignado;
    }

    public String getDestinoRemision() {
        return destinoRemision;
    }

    public void setDestinoRemision(String destinoRemision) {
        this.destinoRemision = destinoRemision;
    }

    public Long getEspecialidadRequeridaId() {
        return especialidadRequeridaId;
    }

    public void setEspecialidadRequeridaId(Long especialidadRequeridaId) {
        this.especialidadRequeridaId = especialidadRequeridaId;
    }

    public MascotaEntity getMascota() {
        return mascota;
    }

    public void setMascota(MascotaEntity mascota) {
        this.mascota = mascota;
    }
}
