package com.vetcare.aplicacion.dto;

import jakarta.validation.constraints.NotBlank;

public record EspecialidadDto(Long id, @NotBlank String nombre, String descripcion, boolean activa) {
}
