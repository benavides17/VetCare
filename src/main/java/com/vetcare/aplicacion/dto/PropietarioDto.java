package com.vetcare.aplicacion.dto;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;

public record PropietarioDto(Long id, @NotBlank String nombre, @NotBlank String apellidos,
                            @NotBlank String tipoDocumento, @NotBlank String numeroDocumento,
                            String telefono, @Email String email, boolean activo) {
}
