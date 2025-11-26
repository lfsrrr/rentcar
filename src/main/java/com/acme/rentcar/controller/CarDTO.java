package com.acme.rentcar.controller;

import com.acme.rentcar.entity.EngineType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO fuer das Anlegen (POST) und Aendern (PUT) eines Autos.
 */
public record CarDTO(
    @Schema(example = "Volkswagen", description = "Der Hersteller des Autos")
    @NotNull(message = "Hersteller darf nicht null sein")
    @Size(min = CarDTO.MIN_HERSTELLER, max = CarDTO.MAX_HERSTELLER, message = "Hersteller fehlerhaft")
    String hersteller,

    @Schema(example = "Golf 8", description = "Das Modell")
    @NotBlank(message = "Modell darf nicht leer sein")
    String modell,

    @Schema(example = "KA-VW-123", description = "Amtliches Kennzeichen")
    @Pattern(regexp = "[A-Z]{1,3}-[A-Z]{1,2}-[0-9]{1,4}", message = "Kennzeichen muss dem Format KA-MA-11 entsprechen")
    @NotBlank
    String kennzeichen,

    @Schema(example = "2024-05-20", description = "Datum der Erstzulassung")
    @NotNull(message = "Erstzulassung darf nicht null sein")
    LocalDate erstzulassung,

    @Schema(example = "DIESEL", description = "Antriebsart (BENZIN, DIESEL, ELEKTRO, HYBRID)")
    @NotNull(message = "Motortyp darf nicht null sein")
    EngineType motor,

    @Schema(example = "5", description = "Anzahl der Sitzplätze (muss positiv sein)")
    @Positive(message = "Sitzplaetze muessen positiv sein")
    int sitzplaetze,

    @Schema(example = "Blau Metallic", description = "Außenfarbe")
    String farbe
) {
    public static final int MIN_HERSTELLER = 2;
    public static final int MAX_HERSTELLER = 50;
}
