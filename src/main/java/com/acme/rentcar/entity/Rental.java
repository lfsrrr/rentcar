package com.acme.rentcar.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

///
/// Repräsentiert einen Mietvorgang.
///
public class Rental {

    private UUID id;
    private LocalDate mietbeginn;
    private LocalDate mietende;
    private BigDecimal gesamtpreis;
    private Customer customer;
    private UUID carId;

    /// Initialisiert das Rental-Objekt.
    @SuppressWarnings("NullAway.Init")
    public Rental() {
    }

    /// Erstellt einen neuen Mietvorgang.
    ///
    /// @param id Die ID.
    /// @param mietbeginn Startdatum.
    /// @param mietende Enddatum.
    /// @param gesamtpreis Der Gesamtpreis.
    /// @param customer Der Kunde.
    /// @param carId Die ID des Autos.
    public Rental(final UUID id, final LocalDate mietbeginn, final LocalDate mietende,
                  final BigDecimal gesamtpreis, final Customer customer, final UUID carId) {
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
            ", mietbeginn=" + mietbeginn +
            ", mietende=" + mietende +
            ", gesamtpreis=" + gesamtpreis +
            ", customer=" + customer +
            ", carId=" + carId +
            '}';
    }

    /// Gibt die ID zurück.
    /// @return Die ID.
    public UUID getId() {
        return id;
    }

    /// Setzt die ID.
    /// @param id Die ID.
    public void setId(final UUID id) {
        this.id = id;
    }

    /// Gibt den Mietbeginn zurück.
    /// @return Das Startdatum.
    public LocalDate getMietbeginn() {
        return mietbeginn;
    }

    /// Setzt den Mietbeginn.
    /// @param mietbeginn Das Startdatum.
    public void setMietbeginn(final LocalDate mietbeginn) {
        this.mietbeginn = mietbeginn;
    }

    /// Gibt das Mietende zurück.
    /// @return Das Enddatum.
    public LocalDate getMietende() {
        return mietende;
    }

    /// Setzt das Mietende.
    /// @param mietende Das Enddatum.
    public void setMietende(final LocalDate mietende) {
        this.mietende = mietende;
    }

    /// Gibt den Gesamtpreis zurück.
    /// @return Der Preis.
    public BigDecimal getGesamtpreis() {
        return gesamtpreis;
    }

    /// Setzt den Gesamtpreis.
    /// @param gesamtpreis Der Preis.
    public void setGesamtpreis(final BigDecimal gesamtpreis) {
        this.gesamtpreis = gesamtpreis;
    }

    /// Gibt den Kunden zurück.
    /// @return Der Kunde.
    public Customer getCustomer() {
        return customer;
    }

    /// Setzt den Kunden.
    /// @param customer Der Kunde.
    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    /// Gibt die Auto-ID zurück.
    /// @return Die Auto-ID.
    public UUID getCarId() {
        return carId;
    }

    /// Setzt die Auto-ID.
    /// @param carId Die Auto-ID.
    public void setCarId(final UUID carId) {
        this.carId = carId;
    }
}
