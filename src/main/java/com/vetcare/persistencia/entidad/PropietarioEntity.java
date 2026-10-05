package com.vetcare.persistencia.entidad;

import com.vetcare.dominio.enumeraciones.TipoDocumento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "propietarios")
public class PropietarioEntity {

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

    @Column(nullable = false)
    private boolean activo = true;

    @ManyToMany
    @JoinTable(
            name = "propietario_mascota",
            joinColumns = @JoinColumn(name = "propietario_id"),
            inverseJoinColumns = @JoinColumn(name = "mascota_id")
    )
    private List<MascotaEntity> mascotas = new ArrayList<>();

    public PropietarioEntity() {
    }

    public PropietarioEntity(String nombre, String apellidos, TipoDocumento tipoDocumento,
                            String numeroDocumento, String telefono, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.telefono = telefono;
        this.email = email;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<MascotaEntity> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<MascotaEntity> mascotas) {
        this.mascotas = mascotas;
    }

    public void agregarMascota(MascotaEntity mascota) {
        if (!mascotas.contains(mascota)) {
            mascotas.add(mascota);
        }
    }

    public void quitarMascota(MascotaEntity mascota) {
        mascotas.remove(mascota);
    }
}
