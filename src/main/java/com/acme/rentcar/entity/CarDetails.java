package com.acme.rentcar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Objects;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class) // Wichtig für created/updated
public class CarDetails {

    @Id
    @GeneratedValue
    @Nullable
    private UUID id;

    // --- Fehlendes Feld: Version ---
    @Version
    private int version;

    @Nullable
    private String farbe;

    private int sitzplaetze;

    @Nullable
    private EngineType motor;

    @Nullable
    private Year baujahr;

    // --- Fehlende Felder: Auditing ---
    @CreatedDate
    @Nullable
    private LocalDateTime created;

    @LastModifiedDate
    @Nullable
    private LocalDateTime updated;

    @SuppressWarnings("NullAway.Init")
    public CarDetails() {
    }

    public CarDetails(@Nullable final UUID id, @Nullable final String farbe, final int sitzplaetze,
                      @Nullable final EngineType motor, @Nullable final Year baujahr) {
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
            ", version=" + version +
            ", farbe='" + farbe + '\'' +
            ", sitzplaetze=" + sitzplaetze +
            ", motor=" + motor +
            ", baujahr=" + baujahr +
            ", created=" + created +
            ", updated=" + updated +
            '}';
    }

    // --- Getter und Setter ---

    @Nullable
    public UUID getId() { return id; }
    public void setId(@Nullable final UUID id) { this.id = id; }

    public int getVersion() { return version; }
    public void setVersion(final int version) { this.version = version; }

    @Nullable
    public String getFarbe() { return farbe; }
    public void setFarbe(@Nullable final String farbe) { this.farbe = farbe; }

    public int getSitzplaetze() { return sitzplaetze; }
    public void setSitzplaetze(final int sitzplaetze) { this.sitzplaetze = sitzplaetze; }

    @Nullable
    public EngineType getMotor() { return motor; }
    public void setMotor(@Nullable final EngineType motor) { this.motor = motor; }

    @Nullable
    public Year getBaujahr() { return baujahr; }
    public void setBaujahr(@Nullable final Year baujahr) { this.baujahr = baujahr; }

    @Nullable
    public LocalDateTime getCreated() { return created; }
    public void setCreated(@Nullable final LocalDateTime created) { this.created = created; }

    @Nullable
    public LocalDateTime getUpdated() { return updated; }
    public void setUpdated(@Nullable final LocalDateTime updated) { this.updated = updated; }
}
