package com.acme.rentcar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/// Repräsentiert einen Mietvorgang.
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Rental {

    @Id
    @GeneratedValue
    @Nullable
    private UUID id;

    @Version
    private int version;

    private LocalDate mietbeginn;
    private LocalDate mietende;
    private BigDecimal gesamtpreis;

    @ManyToOne
    @Nullable
    private Customer customer;

    // Optional: Falls du die Car-ID hier speichern willst
    @Nullable
    private UUID carId;

    @CreatedDate
    @Nullable
    private LocalDateTime created;

    @LastModifiedDate
    @Nullable
    private LocalDateTime updated;

    /// Initialisiert das Rental-Objekt.
    @SuppressWarnings("NullAway.Init")
    public Rental() {
    }

    /// Erstellt einen neuen Mietvorgang.
    public Rental(@Nullable final UUID id, final LocalDate mietbeginn, final LocalDate mietende,
                  final BigDecimal gesamtpreis, @Nullable final Customer customer, @Nullable final UUID carId) {
        this.id = id;
        this.mietbeginn = mietbeginn;
        this.mietende = mietende;
        this.gesamtpreis = gesamtpreis;
        this.customer = customer;
        this.carId = carId;
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof Rental rental && Objects.equals(id, rental.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Rental{" +
            "id=" + id +
            ", version=" + version +
            ", mietbeginn=" + mietbeginn +
            ", mietende=" + mietende +
            ", gesamtpreis=" + gesamtpreis +
            ", customer=" + customer +
            ", carId=" + carId +
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

    public LocalDate getMietbeginn() { return mietbeginn; }
    public void setMietbeginn(final LocalDate mietbeginn) { this.mietbeginn = mietbeginn; }

    public LocalDate getMietende() { return mietende; }
    public void setMietende(final LocalDate mietende) { this.mietende = mietende; }

    public BigDecimal getGesamtpreis() { return gesamtpreis; }
    public void setGesamtpreis(final BigDecimal gesamtpreis) { this.gesamtpreis = gesamtpreis; }

    @Nullable
    public Customer getCustomer() { return customer; }
    public void setCustomer(@Nullable final Customer customer) { this.customer = customer; }

    @Nullable
    public UUID getCarId() { return carId; }
    public void setCarId(@Nullable final UUID carId) { this.carId = carId; }

    @Nullable
    public LocalDateTime getCreated() { return created; }
    public void setCreated(@Nullable final LocalDateTime created) { this.created = created; }

    @Nullable
    public LocalDateTime getUpdated() { return updated; }
    public void setUpdated(@Nullable final LocalDateTime updated) { this.updated = updated; }
}
