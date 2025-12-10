package com.acme.rentcar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.Year;
import java.util.Objects;
import java.util.UUID;
import org.jspecify.annotations.Nullable;

/// Technische Details zu einem Auto als JPA Entity.
@Entity
public class CarDetails {

    @Id
    @GeneratedValue
    @Nullable // Fix: Field can be null before persistence
    private UUID id;

    private String farbe;
    private int sitzplaetze;
    private EngineType motor;
    private Year baujahr;

    /// Erstellt eine leere Instanz von CarDetails (für JPA).
    @SuppressWarnings("NullAway.Init")
    public CarDetails() {
    }

    /// Erstellt neue Auto-Details.
    public CarDetails(@Nullable final UUID id, final String farbe, final int sitzplaetze,
                      final EngineType motor, final Year baujahr) {
        this.id = id;
        this.farbe = farbe;
        this.sitzplaetze = sitzplaetze;
        this.motor = motor;
        this.baujahr = baujahr;
    }

    @Override
    public boolean equals(final Object other) {
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

    @Nullable // Fix: Getter returns nullable
    public UUID getId() {
        return id;
    }

    public void setId(@Nullable final UUID id) { // Fix: Setter accepts nullable
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
