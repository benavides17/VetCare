package com.vetcare.persistencia.entidad;

import com.vetcare.dominio.enumeraciones.DisponibilidadVeterinario;
import com.vetcare.dominio.enumeraciones.TipoDocumento;
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

@Entity
@Table(name = "veterinarios")
public class VeterinarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoDocumento tipoDocumento;

    @Column(nullable = false, length = 30)
    private String numeroDocumento;

    @Column(length = 50)
    private String telefono;

    @Column(length = 150)
    private String email;

    @Column(nullable = false, length = 50)
    private String licencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private DisponibilidadVeterinario disponibilidad = DisponibilidadVeterinario.DISPONIBLE;

    @Column(nullable = false)
    private boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "especialidad_id", nullable = false)
    private EspecialidadEntity especialidad;

    public VeterinarioEntity() {
    }

    public VeterinarioEntity(String nombre, String apellidos, TipoDocumento tipoDocumento,
                            String numeroDocumento, String telefono, String email,
                            String licencia, EspecialidadEntity especialidad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.telefono = telefono;
        this.email = email;
        this.licencia = licencia;
        this.especialidad = especialidad;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public DisponibilidadVeterinario getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadVeterinario disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public EspecialidadEntity getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadEntity especialidad) {
        this.especialidad = especialidad;
    }
}
