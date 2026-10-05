package com.vetcare.dominio.modelo;

import com.vetcare.dominio.valores.Contacto;
import com.vetcare.dominio.valores.Documento;
import com.vetcare.dominio.valores.NombreCompleto;

/**
 * Clase base para todos los actores del sistema clínico.
 *
 * Principio SOLID: S (Responsabilidad única) y L (sustitución de Liskov).
 */
public abstract class Persona {

    private Long id;
    private NombreCompleto nombreCompleto;
    private Documento documento;
    private Contacto contacto;
    private boolean activo;

    protected Persona(Long id, NombreCompleto nombreCompleto, Documento documento, Contacto contacto) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.documento = documento;
        this.contacto = contacto;
        this.activo = true;
    }

    public Long getId() {
        return id;
    }

    public NombreCompleto getNombreCompleto() {
        return nombreCompleto;
    }

    public String nombreCompleto() {
        return nombreCompleto.nombres() + " " + nombreCompleto.apellidos();
    }

    public Documento getDocumento() {
        return documento;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void actualizarContacto(Contacto contacto) {
        if (contacto == null) {
            throw new IllegalArgumentException("El contacto no puede ser nulo.");
        }
        this.contacto = contacto;
    }

    public void desactivar() {
        this.activo = false;
    }
}
