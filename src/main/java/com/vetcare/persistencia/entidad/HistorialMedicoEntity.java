package com.vetcare.persistencia.entidad;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "historiales_medicos")
public class HistorialMedicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mascota_id", nullable = false, unique = true)
    private MascotaEntity mascota;

    @OneToMany(mappedBy = "historialMedico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AtencionEntity> atenciones = new ArrayList<>();

    public HistorialMedicoEntity() {
    }

    public HistorialMedicoEntity(MascotaEntity mascota) {
        this.mascota = mascota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MascotaEntity getMascota() {
        return mascota;
    }

    public void setMascota(MascotaEntity mascota) {
        this.mascota = mascota;
    }

    public List<AtencionEntity> getAtenciones() {
        return atenciones;
    }

    public void setAtenciones(List<AtencionEntity> atenciones) {
        this.atenciones = atenciones;
    }
}
