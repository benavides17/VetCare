package com.vetcare.dominio.modelo;

import com.vetcare.dominio.enumeraciones.DisponibilidadVeterinario;
import com.vetcare.dominio.valores.Contacto;
import com.vetcare.dominio.valores.Documento;
import com.vetcare.dominio.valores.NombreCompleto;

/**
 * Persona profesional con especialidad y disponibilidad, sustituible por cualquier consumidor de Persona (LSP).
 */
public class Veterinario extends Persona {

    private String licencia;
    private DisponibilidadVeterinario disponibilidad;
    private Especialidad especialidad;

    public Veterinario(Long id, NombreCompleto nombreCompleto, Documento documento, Contacto contacto,
                      String licencia, Especialidad especialidad) {
        super(id, nombreCompleto, documento, contacto);
        this.licencia = licencia;
        this.disponibilidad = DisponibilidadVeterinario.DISPONIBLE;
        this.especialidad = especialidad;
    }

    public String getLicencia() {
        return licencia;
    }

    public DisponibilidadVeterinario getDisponibilidad() {
        return disponibilidad;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public boolean estaDisponible() {
        return disponibilidad == DisponibilidadVeterinario.DISPONIBLE;
    }

    public boolean atiende(Especialidad especialidad) {
        return this.especialidad != null && this.especialidad.getId().equals(especialidad.getId());
    }

    public void ocupar() {
        this.disponibilidad = DisponibilidadVeterinario.OCUPADO;
    }

    public void liberar() {
        this.disponibilidad = DisponibilidadVeterinario.DISPONIBLE;
    }

    public void marcarNoDisponible() {
        this.disponibilidad = DisponibilidadVeterinario.NO_DISPONIBLE;
    }
}
