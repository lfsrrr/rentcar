package com.acme.rentcar.controller;

import com.acme.rentcar.entity.EngineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

///
/// DTO fuer das Anlegen (POST) und Aendern (PUT) eines Autos.
///
public record CarDTO(
    @NotNull(message = "Hersteller darf nicht null sein")
    // KORREKTUR: Verwendung von Konstanten statt "Magic Numbers" (2, 50)
    @Size(min = CarDTO.MIN_HERSTELLER, max = CarDTO.MAX_HERSTELLER, message = "Hersteller fehlerhaft")
    String hersteller,

    @NotBlank(message = "Modell darf nicht leer sein")
    String modell,

    @Pattern(regexp = "[A-Z]{1,3}-[A-Z]{1,2}-[0-9]{1,4}", message = "Kennzeichen muss dem Format KA-MA-11 entsprechen")
    @NotBlank
    String kennzeichen,

    @NotNull(message = "Erstzulassung darf nicht null sein")
    LocalDate erstzulassung,

    @NotNull(message = "Motortyp darf nicht null sein")
    EngineType motor,

    @Positive(message = "Sitzplaetze muessen positiv sein")
    int sitzplaetze,

    String farbe
) {
    public static final int MIN_HERSTELLER = 2;
    public static final int MAX_HERSTELLER = 50;
}
