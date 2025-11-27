package com.acme.rentcar.controller;

import java.time.Instant;

/// Ein eigener Record für Fehlermeldungen
///
/// @param timestamp Zeitstempel des Fehlers
/// @param status HTTP Status Code
/// @param error Kurzbeschreibung des Fehlers
/// @param message Detaillierte Fehlermeldung
/// @param path Der aufgerufene Pfad
public record ErrorResponse(
    Instant timestamp,
    int status,
    String error,
    String message,
    String path
) {
}
