package com.acme.rentcar.controller;

import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail; // WICHTIG: ProblemDetail als Rückgabetyp
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

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

        final var problemDetail = ProblemDetail.forStatusAndDetail(ex.getStatusCode(), ex.getReason());

        problemDetail.setTitle(ex.getStatusCode().toString());
        return problemDetail;
    }
}
