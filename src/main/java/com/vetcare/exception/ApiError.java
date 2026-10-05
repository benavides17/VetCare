/**
 * Modelo uniforme para respuestas de error HTTP de la API.
 */
package com.vetcare.exception;

import java.time.OffsetDateTime;

public record ApiError(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
