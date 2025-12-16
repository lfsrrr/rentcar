package com.acme.rentcar.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import org.jspecify.annotations.Nullable; // WICHTIG
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/// Car Entity repräsentiert ein Auto in der Datenbank.
@Entity
@EntityListeners(AuditingEntityListener.class)
@SuppressWarnings("PMD.ShortClassName")
public class Car {

    @Id
    @GeneratedValue
    @Nullable
    private UUID id;

    @Version
    private int version;

    private String hersteller;
    private String modell;
    private LocalDate erstzulassung;
    private String kennzeichen;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Nullable
    private CarDetails details;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals;

    @CreatedDate
    @Nullable // Fix: Kann null sein vor dem Speichern
    private LocalDateTime created;

    @LastModifiedDate
    @Nullable // Fix: Kann null sein vor dem Update
    private LocalDateTime updated;

    @SuppressWarnings("NullAway.Init")
    public Car() {
    }

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

    @Nullable
    public UUID getId() { return id; }
    public void setId(@Nullable final UUID id) { this.id = id; }

    public int getVersion() { return version; }
    public void setVersion(final int version) { this.version = version; }

    public String getHersteller() { return hersteller; }
    public void setHersteller(final String hersteller) { this.hersteller = hersteller; }

    public String getModell() { return modell; }
    public void setModell(final String modell) { this.modell = modell; }

    public LocalDate getErstzulassung() { return erstzulassung; }
    public void setErstzulassung(final LocalDate erstzulassung) { this.erstzulassung = erstzulassung; }

    public String getKennzeichen() { return kennzeichen; }
    public void setKennzeichen(final String kennzeichen) { this.kennzeichen = kennzeichen; }

    @Nullable
    public CarDetails getDetails() { return details; }
    public void setDetails(@Nullable final CarDetails details) { this.details = details; }

    public List<Rental> getRentals() { return rentals; }
    public void setRentals(final List<Rental> rentals) { this.rentals = rentals; }

    @Nullable
    public LocalDateTime getCreated() { return created; }
    public void setCreated(@Nullable final LocalDateTime created) { this.created = created; }

    @Nullable
    public LocalDateTime getUpdated() { return updated; }
    public void setUpdated(@Nullable final LocalDateTime updated) { this.updated = updated; }
}
