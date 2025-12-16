package com.acme.rentcar.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/// Repräsentiert einen Kunden der Autovermietung.
///
/// Ein Kunde ist die zentrale Person, die Mietverträge ([Rental]) abschließt.
/// Diese Entity speichert die persönlichen Stammdaten.
///
/// Technische Hinweise:
/// - Die E-Mail-Adresse dient häufig als fachlicher Identifier (neben der technischen UUID).
/// - Das Geburtsdatum ist relevant für die Prüfung des Mindestalters bei der Anmietung.
@Entity
@EntityListeners(AuditingEntityListener.class)
@SuppressWarnings("checkstyle:HiddenField")
public class Customer {

    /// Die eindeutige ID des Kunden (Primary Key).
    @Id
    @GeneratedValue
    @Nullable
    private UUID id;

    /// Versionsnummer für Optimistic Locking.
    /// Verhindert, dass Stammdaten überschrieben werden, wenn zwei Mitarbeiter
    /// gleichzeitig den Kunden bearbeiten.
    @Version
    private int version;

    /// Der Vorname des Kunden.
    private String vorname;

    /// Der Nachname des Kunden.
    private String nachname;

    /// Die E-Mail-Adresse für Kommunikation und Rechnungsversand.
    private String email;

    /// Das Geburtsdatum des Kunden.
    private LocalDate geburtsdatum;

    /// Zeitstempel der Registrierung/Erstellung.
    @CreatedDate
    @Nullable
    private LocalDateTime created;

    /// Zeitstempel der letzten Profiländerung.
    @LastModifiedDate
    @Nullable
    private LocalDateTime updated;

    /// Standard-Konstruktor für JPA.
    /// Dient der Reflexion und sollte nicht manuell aufgerufen werden.
    @SuppressWarnings("NullAway.Init")
    public Customer() {
    }

    /// Erstellt einen neuen Kunden.
    ///
    /// @param id           Die ID (meist `null` beim Erstellen).
    /// @param vorname      Der Vorname.
    /// @param nachname     Der Nachname.
    /// @param email        Die E-Mail-Adresse.
    /// @param geburtsdatum Das Geburtsdatum.
    public Customer(@Nullable final UUID id, final String vorname, final String nachname,
                    final String email, final LocalDate geburtsdatum) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.geburtsdatum = geburtsdatum;
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof Customer customer && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", version=" + version +
            ", vorname='" + vorname + '\'' +
            ", nachname='" + nachname + '\'' +
            ", email='" + email + '\'' +
            ", geburtsdatum=" + geburtsdatum +
            ", created=" + created +
            ", updated=" + updated +
            '}';
    }

    // --- Getter und Setter ---

    /// Liefert die eindeutige Kunden-ID.
    ///
    /// @return Die UUID oder `null`, falls noch nicht persistiert.
    @Nullable
    public UUID getId() { return id; }

    /// Setzt die Kunden-ID.
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

    /// Liefert den Vornamen des Kunden.
    ///
    /// @return Der Vorname.
    public String getVorname() { return vorname; }

    /// Setzt den Vornamen.
    ///
    /// @param vorname Der neue Vorname.
    public void setVorname(final String vorname) { this.vorname = vorname; }

    /// Liefert den Nachnamen des Kunden.
    ///
    /// @return Der Nachname.
    public String getNachname() { return nachname; }

    /// Setzt den Nachnamen.
    ///
    /// @param nachname Der neue Nachname.
    public void setNachnamen(final String nachname) { this.nachname = nachname; }

    /// Liefert die E-Mail-Adresse.
    ///
    /// @return Die E-Mail als String.
    public String getEmail() { return email; }

    /// Setzt die E-Mail-Adresse.
    ///
    /// @param email Die neue E-Mail.
    public void setEmail(final String email) { this.email = email; }

    /// Liefert das Geburtsdatum.
    ///
    /// @return Das Geburtsdatum.
    public LocalDate getGeburtsdatum() { return geburtsdatum; }

    /// Setzt das Geburtsdatum.
    ///
    /// @param geburtsdatum Das neue Datum.
    public void setGeburtsdatum(final LocalDate geburtsdatum) { this.geburtsdatum = geburtsdatum; }

    /// Liefert den Zeitpunkt der Registrierung.
    ///
    /// @return Das Erstellungsdatum.
    @Nullable
    public LocalDateTime getCreated() { return created; }

    /// Setzt den Zeitpunkt der Registrierung.
    ///
    /// @param created Das neue Datum.
    public void setCreated(@Nullable final LocalDateTime created) { this.created = created; }

    /// Liefert den Zeitpunkt der letzten Änderung.
    ///
    /// @return Das Änderungsdatum.
    @Nullable
    public LocalDateTime getUpdated() { return updated; }

    /// Setzt den Zeitpunkt der letzten Änderung.
    ///
    /// @param updated Das neue Datum.
    public void setUpdated(@Nullable final LocalDateTime updated) { this.updated = updated; }
}
