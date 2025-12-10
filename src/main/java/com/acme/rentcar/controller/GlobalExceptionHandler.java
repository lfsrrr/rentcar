package com.acme.rentcar.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/// Globaler Exception Handler.
///
/// Nutzt nun das Spring 6 Standard-Format ProblemDetail (RFC 7807) anstelle
/// eines eigenen ErrorResponse-Objekts.
@RestControllerAdvice
final class GlobalExceptionHandler {

    /// Erstellt eine Instanz des GlobalExceptionHandler.
    GlobalExceptionHandler() {
    }

    /// Behandelt Validierungsfehler (400).
    ///
    /// @param ex Die aufgetretene Exception.
    /// @param request Der HTTP-Request.
    /// @return Ein ProblemDetail Objekt mit den Validierungsfehlern.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail onMethodArgumentNotValid(
        final MethodArgumentNotValidException ex,
        final HttpServletRequest request
    ) {
        final var details = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));

        final var problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            details
        );

        problemDetail.setTitle("Validation Failed");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return problemDetail;
    }

    /// Behandelt ResponseStatusExceptions (z.B. 404 Not Found).
    ///
    /// @param ex Die aufgetretene Exception.
    /// @param request Der HTTP-Request.
    /// @return Ein ProblemDetail Objekt.
    @ExceptionHandler(ResponseStatusException.class)
    ProblemDetail onResponseStatus(
        final ResponseStatusException ex,
        final HttpServletRequest request
    ) {
        final String message = Objects.requireNonNullElse(ex.getReason(), "Unknown Error");

        final var problemDetail = ProblemDetail.forStatusAndDetail(
            ex.getStatusCode(),
            message
        );

        problemDetail.setTitle("Error");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return problemDetail;
    }
}
