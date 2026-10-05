package com.vetcare.aplicacion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VeterinarioDto(Long id, @NotBlank String nombre, @NotBlank String apellidos,
                            @NotBlank String tipoDocumento, @NotBlank String numeroDocumento,
                            String telefono, @Email String email, @NotBlank String licencia,
                            String disponibilidad, boolean activo, @NotNull Long especialidadId,
                            String especialidadNombre) {
}
