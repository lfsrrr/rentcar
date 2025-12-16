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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/// Kunde der Autovermietung.
@Entity
@EntityListeners(AuditingEntityListener.class)
@SuppressWarnings("checkstyle:HiddenField")
public class Customer {

    @Id
    @GeneratedValue
    @Nullable
    private UUID id;

    @Version
    private int version;

    private String vorname;
    private String nachname;
    private String email;
    private LocalDate geburtsdatum;

    @CreatedDate
    @Nullable
    private LocalDateTime created;

    @LastModifiedDate
    @Nullable
    private LocalDateTime updated;

    /// Initialisiert das Customer-Objekt (Standardkonstruktor).
    @SuppressWarnings("NullAway.Init")
    public Customer() {
    }

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

    @Nullable
    public UUID getId() { return id; }
    public void setId(@Nullable final UUID id) { this.id = id; }

    public int getVersion() { return version; }
    public void setVersion(final int version) { this.version = version; }

    public String getVorname() { return vorname; }
    public void setVorname(final String vorname) { this.vorname = vorname; }

    public String getNachname() { return nachname; }
    public void setNachnamen(final String nachname) { this.nachname = nachname; }

    public String getEmail() { return email; }
    public void setEmail(final String email) { this.email = email; }

    public LocalDate getGeburtsdatum() { return geburtsdatum; }
    public void setGeburtsdatum(final LocalDate geburtsdatum) { this.geburtsdatum = geburtsdatum; }

    @Nullable
    public LocalDateTime getCreated() { return created; }
    public void setCreated(@Nullable final LocalDateTime created) { this.created = created; }

    @Nullable
    public LocalDateTime getUpdated() { return updated; }
    public void setUpdated(@Nullable final LocalDateTime updated) { this.updated = updated; }
}
