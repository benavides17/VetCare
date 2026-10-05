package com.vetcare.dominio.creacion;

import com.vetcare.dominio.enumeraciones.Sexo;
import com.vetcare.dominio.modelo.Mascota;
import java.time.LocalDate;

/**
 * Patrón Builder: permite construir una mascota de forma fluida validando sus datos obligatorios.
 */
public class MascotaBuilder {

    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private Sexo sexo;
    private LocalDate fechaNacimiento;
    private String color;
    private Double peso;
    private String observaciones;

    public MascotaBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public MascotaBuilder nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public MascotaBuilder especie(String especie) {
        this.especie = especie;
        return this;
    }

    public MascotaBuilder raza(String raza) {
        this.raza = raza;
        return this;
    }

    public MascotaBuilder sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public MascotaBuilder fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public MascotaBuilder color(String color) {
        this.color = color;
        return this;
    }

    public MascotaBuilder peso(Double peso) {
        this.peso = peso;
        return this;
    }

    public MascotaBuilder observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public Mascota build() {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio.");
        }
        if (especie == null || especie.isBlank()) {
            throw new IllegalArgumentException("La especie es obligatoria.");
        }
        if (raza == null || raza.isBlank()) {
            throw new IllegalArgumentException("La raza es obligatoria.");
        }
        if (sexo == null) {
            throw new IllegalArgumentException("El sexo es obligatorio.");
        }
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("El color es obligatorio.");
        }
        if (peso == null || peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero.");
        }
        return new Mascota(id, nombre, especie, raza, sexo, fechaNacimiento, color, peso, observaciones);
    }
}
