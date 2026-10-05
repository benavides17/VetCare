package com.vetcare.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Centraliza la gestión de errores HTTP para responder con un formato uniforme.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiError> handleRecursoNoEncontrado(RecursoNoEncontradoException ex,
                                                             HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(PropietarioDuplicadoException.class)
    public ResponseEntity<ApiError> handlePropietarioDuplicado(PropietarioDuplicadoException ex,
                                                              HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({ConflictoHorarioException.class, MascotaNoPerteneceAPropietarioException.class,
            VeterinarioNoDisponibleException.class, ReglaNegocioException.class})
    public ResponseEntity<ApiError> handleReglasNegocio(VetCareException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(TransicionEstadoInvalidaException.class)
    public ResponseEntity<ApiError> handleTransicionEstado(TransicionEstadoInvalidaException ex,
                                                          HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpServletRequest request) {
        String detalle = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toFieldErrorMessage)
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", detalle, request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleBodyNoLegible(HttpMessageNotReadableException ex,
                                                        HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request",
                "El cuerpo de la solicitud no tiene un formato válido.", request.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex,
                                                            HttpServletRequest request) {
        String mensaje = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", mensaje, request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleArgumentoInvalido(IllegalArgumentException ex,
                                                           HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Error no controlado en la API", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                "Ocurrió un error inesperado en el servidor.", request.getRequestURI());
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String error, String message, String path) {
        ApiError apiError = new ApiError(OffsetDateTime.now(), status.value(), error, message, path);
        return ResponseEntity.status(status).body(apiError);
    }

    private String toFieldErrorMessage(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}
