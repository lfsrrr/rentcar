package com.acme.rentcar.controller;

import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * Faengt Validierungsfehler (400) und 404 (Not Found) global ab.
 */

@RestControllerAdvice
final class GlobalExceptionHandler {

    @ExceptionHandler
    ProblemDetail onMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
        final var details = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));

        final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, details);
        problemDetail.setTitle("Validation Failed");
        return problemDetail;
    }

    @ExceptionHandler
    ProblemDetail onResponseStatus(final ResponseStatusException ex) {
        final var problemDetail = ProblemDetail.forStatus(ex.getStatusCode());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setDetail(ex.getReason());
        return problemDetail;
    }
}
