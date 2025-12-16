package com.acme.rentcar.controller;

import com.acme.rentcar.entity.EngineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.UUID;
import org.jspecify.annotations.Nullable;

/// Datentransferobjekt für Auto-Daten.
public record CarDTO(
    @Nullable UUID id, // ID hinzufügen für Response
    @NotBlank String hersteller,
    @NotBlank String modell,
    @NotBlank String kennzeichen,
    @NotNull @PastOrPresent LocalDate erstzulassung,
    @NotNull EngineType motor,
    @Positive int sitzplaetze,
    @NotBlank String farbe
) {
    // Konstruktor für Komfort ohne ID (für Tests/Create)
    public CarDTO(String hersteller, String modell, String kennzeichen,
                  LocalDate erstzulassung, EngineType motor, int sitzplaetze, String farbe) {
        this(null, hersteller, modell, kennzeichen, erstzulassung, motor, sitzplaetze, farbe);
    }
}
