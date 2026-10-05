package com.vetcare.dominio.modelo;

import com.vetcare.dominio.valores.Contacto;
import com.vetcare.dominio.valores.Documento;
import com.vetcare.dominio.valores.NombreCompleto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Persona responsable de una o varias mascotas; conserva el comportamiento común de la abstracción Persona.
 */
public class Propietario extends Persona {

    private final List<Mascota> mascotas = new ArrayList<>();

    public Propietario(Long id, NombreCompleto nombreCompleto, Documento documento, Contacto contacto) {
        super(id, nombreCompleto, documento, contacto);
    }

    public List<Mascota> getMascotas() {
        return Collections.unmodifiableList(mascotas);
    }

    public void agregarMascota(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula.");
        }
        if (!mascotas.contains(mascota)) {
            mascotas.add(mascota);
            mascota.agregarPropietario(this);
        }
    }

    public void quitarMascota(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula.");
        }
        mascotas.remove(mascota);
        mascota.removerPropietario(this);
    }

    public boolean tieneMascota(Mascota mascota) {
        return mascotas.contains(mascota);
    }
}
