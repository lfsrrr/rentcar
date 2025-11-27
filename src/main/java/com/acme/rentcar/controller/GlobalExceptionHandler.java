package com.acme.rentcar.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/// Globaler Exception Handler.
@RestControllerAdvice
final class GlobalExceptionHandler {

    /// Erstellt eine Instanz des GlobalExceptionHandler.
    GlobalExceptionHandler() {
    }

    /// Behandelt Validierungsfehler (400).
    ///
    /// @param ex Die aufgetretene Exception.
    /// @param request Der HTTP-Request.
    /// @return Die Fehlerantwort als JSON.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> onMethodArgumentNotValid(
        final MethodArgumentNotValidException ex,
        final HttpServletRequest request
    ) {
        final var details = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));

        final var errorResponse = new ErrorResponse(
            Instant.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Validation Failed",
            details,
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /// Behandelt Not Found Fehler (404).
    ///
    /// @param ex Die aufgetretene Exception.
    /// @param request Der HTTP-Request.
    /// @return Die Fehlerantwort als JSON.
    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<ErrorResponse> onResponseStatus(
        final ResponseStatusException ex,
        final HttpServletRequest request
    ) {
        final String message = Objects.requireNonNullElse(ex.getReason(), "Unknown Error");

        final var errorResponse = new ErrorResponse(
            Instant.now(),
            ex.getStatusCode().value(),
            "Error",
            message,
            request.getRequestURI()
        );

        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }
}
