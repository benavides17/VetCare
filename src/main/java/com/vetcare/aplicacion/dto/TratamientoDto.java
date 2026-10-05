package com.vetcare.aplicacion.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TratamientoDto(Long id, @NotBlank String medicamento,
                            @NotNull @DecimalMin(value = "0.01") BigDecimal dosisCantidad,
                            @NotBlank String unidad, @NotBlank String frecuencia,
                            @NotNull LocalDate fechaInicio,
                            @NotNull LocalDate fechaFin, String indicaciones, String estado,
                            @NotNull Long atencionId) {
}
