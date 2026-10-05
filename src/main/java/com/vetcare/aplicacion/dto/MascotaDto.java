package com.vetcare.aplicacion.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record MascotaDto(Long id, @NotBlank String nombre, @NotBlank String especie,
                        String raza, @NotBlank String sexo,
                        @NotNull @Past LocalDate fechaNacimiento, String color,
                        @NotNull @DecimalMin(value = "0.01") Double peso,
                        String observaciones, boolean activa) {
}
