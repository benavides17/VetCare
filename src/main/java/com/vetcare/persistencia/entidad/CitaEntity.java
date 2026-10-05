package com.vetcare.persistencia.entidad;

import com.vetcare.dominio.enumeraciones.EstadoCita;
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
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
public class CitaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false, length = 255)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private EstadoCita estado = EstadoCita.PROGRAMADA;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mascota_id", nullable = false)
    private MascotaEntity mascota;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "veterinario_id", nullable = false)
    private VeterinarioEntity veterinario;

    public CitaEntity() {
    }

    public CitaEntity(LocalDateTime fechaHora, String motivo, MascotaEntity mascota,
                     VeterinarioEntity veterinario) {
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.mascota = mascota;
        this.veterinario = veterinario;
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

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public MascotaEntity getMascota() {
        return mascota;
    }

    public void setMascota(MascotaEntity mascota) {
        this.mascota = mascota;
    }

    public VeterinarioEntity getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(VeterinarioEntity veterinario) {
        this.veterinario = veterinario;
    }
}
