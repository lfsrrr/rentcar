package com.acme.rentcar.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/// Repräsentiert ein Kraftfahrzeug in der Datenbank.
///
/// Diese Entity ist der zentrale Bestandteil der Autovermietung. Sie speichert die
/// Stammdaten eines Fahrzeugs (wie Hersteller und Modell) und hält Referenzen zu
/// technischen Details sowie zu den getätigten Mietvorgängen.
///
/// Technische Hinweise:
/// - Verwendet [AuditingEntityListener] für automatische Zeitstempel.
/// - Unterstützt Optimistic Locking via `@Version`.
@Entity
@EntityListeners(AuditingEntityListener.class)
@SuppressWarnings("PMD.ShortClassName")
public class Car {

    /// Die eindeutige ID des Autos (Primary Key).
    /// Wird beim Persistieren automatisch durch die Datenbank generiert.
    @Id
    @GeneratedValue
    @Nullable
    private UUID id;

    /// Versionsnummer für Optimistic Locking.
    /// Verhindert "Lost Updates", wenn mehrere Transaktionen gleichzeitig
    /// das gleiche Auto bearbeiten wollen.
    @Version
    private int version;

    /// Der Name des Herstellers (z.B. "BMW", "Volkswagen").
    private String hersteller;

    /// Die Modellbezeichnung des Fahrzeugs (z.B. "Golf 8", "M4 Competition").
    private String modell;

    /// Das Datum der Erstzulassung.
    private LocalDate erstzulassung;

    /// Das amtliche Kennzeichen des Fahrzeugs (z.B. "KA-AB-123").
    private String kennzeichen;

    /// Technische Details zum Auto (1:1 Beziehung).
    ///
    /// Diese Beziehung ist als `LAZY` konfiguriert, d.h. die Details werden erst
    /// bei direktem Zugriff aus der Datenbank geladen, um Performance zu sparen.
    /// `CascadeType.ALL` sorgt dafür, dass Änderungen hier automatisch gespeichert werden.
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Nullable
    private CarDetails details;

    /// Liste der Mietvorgänge für dieses Auto (1:N Beziehung).
    /// Ein Auto kann beliebig oft vermietet werden.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals;

    /// Zeitstempel der Erstellung.
    /// Wird automatisch von Spring Data JPA gesetzt.
    @CreatedDate
    @Nullable
    private LocalDateTime created;

    /// Zeitstempel der letzten Änderung.
    /// Wird automatisch aktualisiert, sobald sich ein Feld ändert.
    @LastModifiedDate
    @Nullable
    private LocalDateTime updated;

    /// Standard-Konstruktor für JPA.
    /// Darf nicht direkt im Code verwendet werden, sondern dient nur der Reflexion durch Hibernate.
    @SuppressWarnings("NullAway.Init")
    public Car() {
    }

    /// Erstellt ein neues Auto mit allen Eigenschaften.
    ///
    /// @param id            Die ID (meist `null` beim Erstellen).
    /// @param hersteller    Der Herstellername.
    /// @param modell        Das Fahrzeugmodell.
    /// @param erstzulassung Datum der ersten Zulassung.
    /// @param kennzeichen   Das Kennzeichen.
    /// @param details       Zusätzliche technische Details (optional).
    /// @param rentals       Liste der bisherigen Vermietungen.
    public Car(@Nullable final UUID id, final String hersteller, final String modell, final LocalDate erstzulassung,
               final String kennzeichen, @Nullable final CarDetails details, final List<Rental> rentals) {
        this.id = id;
        this.hersteller = hersteller;
        this.modell = modell;
        this.erstzulassung = erstzulassung;
        this.kennzeichen = kennzeichen;
        this.details = details;
        this.rentals = rentals;
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof Car car && Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + id +
            ", version=" + version +
            ", hersteller='" + hersteller + '\'' +
            ", modell='" + modell + '\'' +
            ", erstzulassung=" + erstzulassung +
            ", kennzeichen='" + kennzeichen + '\'' +
            ", details=" + details +
            ", rentals=" + rentals +
            ", created=" + created +
            ", updated=" + updated +
            '}';
    }

    // --- Getter und Setter ---

    /// Liefert die Datenbank-ID des Autos.
    ///
    /// @return Die UUID oder `null`, falls noch nicht persistiert.
    @Nullable
    public UUID getId() { return id; }

    /// Setzt die Datenbank-ID.
    ///
    /// @param id Die neue UUID.
    public void setId(@Nullable final UUID id) { this.id = id; }

    /// Liefert die aktuelle Versionsnummer.
    ///
    /// @return Die Version für Optimistic Locking.
    public int getVersion() { return version; }

    /// Setzt die Versionsnummer (meistens nur intern genutzt).
    ///
    /// @param version Die neue Versionsnummer.
    public void setVersion(final int version) { this.version = version; }

    /// Liefert den Hersteller.
    ///
    /// @return Der Name des Herstellers.
    public String getHersteller() { return hersteller; }

    /// Setzt den Hersteller.
    ///
    /// @param hersteller Der neue Herstellername.
    public void setHersteller(final String hersteller) { this.hersteller = hersteller; }

    /// Liefert das Modell.
    ///
    /// @return Die Modellbezeichnung.
    public String getModell() { return modell; }

    /// Setzt das Modell.
    ///
    /// @param modell Das neue Modell.
    public void setModell(final String modell) { this.modell = modell; }

    /// Liefert das Erstzulassungsdatum.
    ///
    /// @return Das Datum der Erstzulassung.
    public LocalDate getErstzulassung() { return erstzulassung; }

    /// Setzt das Erstzulassungsdatum.
    ///
    /// @param erstzulassung Das neue Datum.
    public void setErstzulassung(final LocalDate erstzulassung) { this.erstzulassung = erstzulassung; }

    /// Liefert das Kennzeichen.
    ///
    /// @return Das amtliche Kennzeichen.
    public String getKennzeichen() { return kennzeichen; }

    /// Setzt das Kennzeichen.
    ///
    /// @param kennzeichen Das neue Kennzeichen.
    public void setKennzeichen(final String kennzeichen) { this.kennzeichen = kennzeichen; }

    /// Liefert die technischen Details.
    /// Hinweis: Kann `null` sein, wenn keine Details hinterlegt sind.
    ///
    /// @return Das CarDetails-Objekt oder `null`.
    @Nullable
    public CarDetails getDetails() { return details; }

    /// Verknüpft technische Details mit diesem Auto.
    ///
    /// @param details Die zu verknüpfenden Details.
    public void setDetails(@Nullable final CarDetails details) { this.details = details; }

    /// Liefert die Liste aller Mietvorgänge.
    ///
    /// @return Eine Liste der Rentals.
    public List<Rental> getRentals() { return rentals; }

    /// Setzt die Liste der Mietvorgänge.
    ///
    /// @param rentals Die neue Liste.
    public void setRentals(final List<Rental> rentals) { this.rentals = rentals; }

    /// Liefert den Zeitstempel der Erstellung.
    ///
    /// @return Der Zeitpunkt der Erstellung.
    @Nullable
    public LocalDateTime getCreated() { return created; }

    /// Setzt den Erstellungszeitpunkt.
    ///
    /// @param created Der neue Zeitpunkt.
    public void setCreated(@Nullable final LocalDateTime created) { this.created = created; }

    /// Liefert den Zeitstempel der letzten Änderung.
    ///
    /// @return Der Zeitpunkt der letzten Aktualisierung.
    @Nullable
    public LocalDateTime getUpdated() { return updated; }

    /// Setzt den Änderungszeitpunkt.
    ///
    /// @param updated Der neue Zeitpunkt.
    public void setUpdated(@Nullable final LocalDateTime updated) { this.updated = updated; }
}
