package com.acme.rentcar.entity;

import java.time.Year;
import java.util.Objects;
import java.util.UUID;

/// Technische Details zu einem Auto.
public class CarDetails {

    private UUID id;
    private String farbe;
    private int sitzplaetze;
    private EngineType motor;
    private Year baujahr;

    /// Erstellt eine leere Instanz von CarDetails.
    @SuppressWarnings("NullAway.Init")
    public CarDetails() {
    }

    /// Erstellt neue Auto-Details mit allen Attributen.
    ///
    /// @param id Die ID des Detail-Datensatzes.
    /// @param farbe Die Lackierung des Autos.
    /// @param sitzplaetze Die Anzahl der verfügbaren Sitze.
    /// @param motor Der verbaute Motortyp.
    /// @param baujahr Das Baujahr des Fahrzeugs.
    public CarDetails(final UUID id, final String farbe, final int sitzplaetze,
                      final EngineType motor, final Year baujahr) {
        this.id = id;
        this.farbe = farbe;
        this.sitzplaetze = sitzplaetze;
        this.motor = motor;
        this.baujahr = baujahr;
    }

    /// Prüft, ob dieses Objekt gleich einem anderen Objekt ist.
    ///
    /// @param other Das zu vergleichende Objekt.
    /// @return true, wenn die Objekte die gleiche ID haben, sonst false.
    @Override
    public boolean equals(final Object other) {
        return other instanceof CarDetails details && Objects.equals(id, details.id);
    }

    /// Berechnet den Hash-Code für dieses Objekt.
    ///
    /// @return Der Hash-Code basierend auf der ID.
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /// Gibt eine String-Repräsentation der Auto-Details zurück.
    ///
    /// @return Ein String, der die Eigenschaften enthält.
    @Override
    public String toString() {
        return "CarDetails{" +
            "id=" + id +
            ", farbe='" + farbe + '\'' +
            ", sitzplaetze=" + sitzplaetze +
            ", motor=" + motor +
            ", baujahr=" + baujahr +
            '}';
    }

    // --- Getter und Setter ---

    /// Ruft die ID ab.
    ///
    /// @return Die UUID der Details.
    public UUID getId() {
        return id;
    }

    /// Setzt die ID.
    ///
    /// @param id Die neue UUID.
    public void setId(final UUID id) {
        this.id = id;
    }

    /// Ruft die Farbe ab.
    ///
    /// @return Die Farbe als String.
    public String getFarbe() {
        return farbe;
    }

    /// Setzt die Farbe.
    ///
    /// @param farbe Die neue Farbe.
    public void setFarbe(final String farbe) {
        this.farbe = farbe;
    }

    /// Ruft die Anzahl der Sitzplätze ab.
    ///
    /// @return Die Anzahl der Sitze.
    public int getSitzplaetze() {
        return sitzplaetze;
    }

    /// Setzt die Anzahl der Sitzplätze.
    ///
    /// @param sitzplaetze Die neue Anzahl der Sitze.
    public void setSitzplaetze(final int sitzplaetze) {
        this.sitzplaetze = sitzplaetze;
    }

    /// Ruft den Motortyp ab.
    ///
    /// @return Der EngineType.
    public EngineType getMotor() {
        return motor;
    }

    /// Setzt den Motortyp.
    ///
    /// @param motor Der neue EngineType.
    public void setMotor(final EngineType motor) {
        this.motor = motor;
    }

    /// Ruft das Baujahr ab.
    ///
    /// @return Das Baujahr als Year-Objekt.
    public Year getBaujahr() {
        return baujahr;
    }

    /// Setzt das Baujahr.
    ///
    /// @param baujahr Das neue Baujahr.
    public void setBaujahr(final Year baujahr) {
        this.baujahr = baujahr;
    }
}
