package com.vetcare.dominio.modelo;

/**
 * Especialidad profesional de un veterinario.
 */
public class Especialidad {

    private Long id;
    private String nombre;
    private String descripcion;
    private boolean activa;

    public Especialidad(Long id, String nombre, String descripcion, boolean activa) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activa = activa;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isActiva() {
        return activa;
    }
}
