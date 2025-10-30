package com.acme.rentcar.entity; // Paketname angepasst

import java.time.Year;
import java.util.Objects;
import java.util.UUID;

// Diese Klasse hält die Details zum Car (1:1 Beziehung)
public class CarDetails {

    private UUID id;
    private String farbe;
    private int sitzplaetze;
    private EngineType motor; // Verwendet das neue Enum
    private Year baujahr;

    // Optional: Leerer Konstruktor für JPA/Hibernate
    public CarDetails() {
        // Leerer Konstruktor
    }

    // Voller Konstruktor
    public CarDetails(final UUID id, final String farbe, final int sitzplaetze,
                      final EngineType motor, final Year baujahr) {
        this.id = id;
        this.farbe = farbe;
        this.sitzplaetze = sitzplaetze;
        this.motor = motor;
        this.baujahr = baujahr;
    }

    // --- Core Java Methoden ---

    @Override
    public boolean equals(final Object other) {
        // equals/hashCode sollte immer auf dem ID-Feld basieren
        return other instanceof CarDetails details && Objects.equals(id, details.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

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

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(final String farbe) {
        this.farbe = farbe;
    }

    public int getSitzplaetze() {
        return sitzplaetze;
    }

    public void setSitzplaetze(final int sitzplaetze) {
        this.sitzplaetze = sitzplaetze;
    }

    public EngineType getMotor() {
        return motor;
    }

    public void setMotor(final EngineType motor) {
        this.motor = motor;
    }

    public Year getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(final Year baujahr) {
        this.baujahr = baujahr;
    }
}
