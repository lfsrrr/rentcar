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

/// Repräsentiert die technischen Details eines Autos.
///
/// Diese Entity ist als eigenständige Tabelle modelliert, die in einer 1:1-Beziehung
/// zur [Car]-Entity steht. Durch die Auslagerung können diese Daten bei Bedarf
/// "lazy" (verzögert) geladen werden, was die Performance bei Listenabfragen verbessert.
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CarDetails {

    /// Die eindeutige ID der Details (Primary Key).
    @Id
    @GeneratedValue
    @Nullable
    private UUID id;

    /// Versionsnummer für Optimistic Locking (verhindert Lost Updates).
    @Version
    private int version;

    /// Die Lackierung des Fahrzeugs (z.B. "Schwarz", "Metallic Blau").
    @Nullable
    private String farbe;

    /// Die Anzahl der Sitzplätze.
    private int sitzplaetze;

    /// Die Antriebsart (z.B. DIESEL, ELEKTRO).
    @Nullable
    private EngineType motor;

    /// Das Baujahr des Fahrzeugs.
    @Nullable
    private Year baujahr;

    /// Zeitstempel der Erstellung (automatisch gesetzt).
    @CreatedDate
    @Nullable
    private LocalDateTime created;

    /// Zeitstempel der letzten Änderung (automatisch aktualisiert).
    @LastModifiedDate
    @Nullable
    private LocalDateTime updated;

    /// Standard-Konstruktor für JPA.
    @SuppressWarnings("NullAway.Init")
    public CarDetails() {
    }

    /// Erstellt neue Fahrzeugdetails.
    ///
    /// @param id          Die ID (meist `null` beim Erstellen).
    /// @param farbe       Die Farbe.
    /// @param sitzplaetze Anzahl der Sitze.
    /// @param motor       Antriebsart.
    /// @param baujahr     Baujahr.
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

    /// Liefert die eindeutige ID der Details.
    ///
    /// @return Die UUID oder `null`, falls noch nicht gespeichert.
    @Nullable
    public UUID getId() { return id; }

    /// Setzt die ID der Details.
    ///
    /// @param id Die neue UUID.
    public void setId(@Nullable final UUID id) { this.id = id; }

    /// Liefert die Versionsnummer für Optimistic Locking.
    ///
    /// @return Die aktuelle Version.
    public int getVersion() { return version; }

    /// Setzt die Versionsnummer.
    ///
    /// @param version Die neue Version.
    public void setVersion(final int version) { this.version = version; }

    /// Liefert die Außenlackierung.
    ///
    /// @return Die Farbe als String.
    @Nullable
    public String getFarbe() { return farbe; }

    /// Setzt die Außenlackierung.
    ///
    /// @param farbe Die neue Farbe.
    public void setFarbe(@Nullable final String farbe) { this.farbe = farbe; }

    /// Liefert die Anzahl der verfügbaren Sitzplätze.
    ///
    /// @return Die Anzahl der Sitze.
    public int getSitzplaetze() { return sitzplaetze; }

    /// Setzt die Anzahl der Sitzplätze.
    ///
    /// @param sitzplaetze Die neue Anzahl.
    public void setSitzplaetze(final int sitzplaetze) { this.sitzplaetze = sitzplaetze; }

    /// Liefert den Antriebstyp des Fahrzeugs.
    ///
    /// @return Der Motortyp (z.B. BENZIN, DIESEL).
    @Nullable
    public EngineType getMotor() { return motor; }

    /// Setzt den Antriebstyp.
    ///
    /// @param motor Der neue Motortyp.
    public void setMotor(@Nullable final EngineType motor) { this.motor = motor; }

    /// Liefert das Baujahr des Fahrzeugs.
    ///
    /// @return Das Baujahr als [Year]-Objekt.
    @Nullable
    public Year getBaujahr() { return baujahr; }

    /// Setzt das Baujahr.
    ///
    /// @param baujahr Das neue Baujahr.
    public void setBaujahr(@Nullable final Year baujahr) { this.baujahr = baujahr; }

    /// Liefert den Zeitpunkt der Erstellung des Datensatzes.
    ///
    /// @return Das Erstellungsdatum.
    @Nullable
    public LocalDateTime getCreated() { return created; }

    /// Setzt das Erstellungsdatum (meist automatisch durch JPA).
    ///
    /// @param created Das neue Datum.
    public void setCreated(@Nullable final LocalDateTime created) { this.created = created; }

    /// Liefert den Zeitpunkt der letzten Änderung.
    ///
    /// @return Das Änderungsdatum.
    @Nullable
    public LocalDateTime getUpdated() { return updated; }

    /// Setzt das Änderungsdatum (meist automatisch durch JPA).
    ///
    /// @param updated Das neue Datum.
    public void setUpdated(@Nullable final LocalDateTime updated) { this.updated = updated; }
}
