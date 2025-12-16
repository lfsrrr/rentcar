package com.acme.rentcar.controller;

import com.acme.rentcar.entity.EngineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.UUID;
import org.jspecify.annotations.Nullable;

/// Datentransferobjekt (DTO) für die Übermittlung von Fahrzeugdaten zwischen Client und Server.
///
/// Dieses Record dient sowohl für die Erstellung neuer Fahrzeuge (wo die ID noch `null` ist),
/// als auch für die Rückgabe von Fahrzeugdaten an den Client.
///
/// @param id            Die eindeutige UUID des Fahrzeugs. Ist beim Anlegen eines neuen Autos `null`,
///                      da die ID erst von der Datenbank generiert wird.
/// @param hersteller    Der Name des Fahrzeugherstellers (z.B. BMW, Volkswagen).
///                      Darf nicht leer sein.
/// @param modell        Die genaue Modellbezeichnung (z.B. Golf 8, M4 Competition).
///                      Darf nicht leer sein.
/// @param kennzeichen   Das amtliche Kennzeichen des Fahrzeugs (z.B. KA-XY-123).
///                      Darf nicht leer sein.
/// @param erstzulassung Das Datum, an dem das Fahrzeug zum ersten Mal zugelassen wurde.
///                      Muss in der Vergangenheit oder heute liegen.
/// @param motor         Der Antriebstyp des Fahrzeugs (z.B. DIESEL, BENZIN).
///                      Darf nicht `null` sein.
/// @param sitzplaetze   Die Anzahl der verfügbaren Sitzplätze. Muss eine positive Zahl sein (größer 0).
/// @param farbe         Die Außenlackierung des Fahrzeugs (z.B. Metallic Blau).
///                      Darf nicht leer sein.
public record CarDTO(
    @Nullable UUID id,
    @NotBlank String hersteller,
    @NotBlank String modell,
    @NotBlank String kennzeichen,
    @NotNull @PastOrPresent LocalDate erstzulassung,
    @NotNull EngineType motor,
    @Positive int sitzplaetze,
    @NotBlank String farbe
) {

    /// Erstellt ein neues [CarDTO] ohne ID.
    ///
    /// Dieser Konstruktor ist besonders nützlich für Unit-Tests oder beim Anlegen neuer Fahrzeuge,
    /// wenn noch keine Datenbank-ID existiert.
    ///
    /// @param hersteller    Der Hersteller des Autos.
    /// @param modell        Das Modell des Autos.
    /// @param kennzeichen   Das Kennzeichen.
    /// @param erstzulassung Das Datum der Erstzulassung.
    /// @param motor         Der Motortyp.
    /// @param sitzplaetze   Anzahl der Sitze.
    /// @param farbe         Die Farbe des Autos.
    public CarDTO(final String hersteller, final String modell, final String kennzeichen,
                  final LocalDate erstzulassung, final EngineType motor, final int sitzplaetze, final String farbe) {
        this(null, hersteller, modell, kennzeichen, erstzulassung, motor, sitzplaetze, farbe);
    }
}
