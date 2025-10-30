package com.acme.rentcar.entity; // Paketname auf rentcar.entity angepasst

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

// Diese Klasse repräsentiert einen einzelnen Mietvorgang (N-Seite der 1:N-Beziehung zu Car)
public class Rental {

    private UUID id;
    private LocalDate mietbeginn; // Startdatum
    private LocalDate mietende;   // Enddatum
    private double gesamtpreis; // Mietpreis

    // 1:1 Beziehung zu Customer (Jeder Mietvertrag hat einen verantwortlichen Kunden)
    private Customer customer;

    // 1:N Beziehung zu Car (Das Car-Objekt enthält eine Liste dieser Rentals)
    private UUID carId; // Fremdschlüssel zur Car-Entity

    // Optional: Leerer Konstruktor für JPA/Hibernate
    public Rental() {
        // Leerer Konstruktor
    }

    // Voller Konstruktor
    public Rental(final UUID id, final LocalDate mietbeginn, final LocalDate mietende,
                  final double gesamtpreis, final Customer customer, final UUID carId) {
        this.id = id;
        this.mietbeginn = mietbeginn;
        this.mietende = mietende;
        this.gesamtpreis = gesamtpreis;
        this.customer = customer;
        this.carId = carId;
    }

    // --- Core Java Methoden ---

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

    // --- Getter und Setter ---

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public LocalDate getMietbeginn() {
        return mietbeginn;
    }

    public void setMietbeginn(final LocalDate mietbeginn) {
        this.mietbeginn = mietbeginn;
    }

    public LocalDate getMietende() {
        return mietende;
    }

    public void setMietende(final LocalDate mietende) {
        this.mietende = mietende;
    }

    public double getGesamtpreis() {
        return gesamtpreis;
    }

    public void setGesamtpreis(final double gesamtpreis) {
        this.gesamtpreis = gesamtpreis;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(final UUID carId) {
        this.carId = carId;
    }
}
