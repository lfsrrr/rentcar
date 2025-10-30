package com.acme.rentcar.entity; // Paketname auf rentcar.entity angepasst

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

// Diese Klasse hält die Kundeninformationen
public class Customer {

    private UUID id;
    private String vorname;
    private String nachname;
    private String email;
    private LocalDate geburtsdatum;

    // Optional: Leerer Konstruktor für JPA/Hibernate
    public Customer() {
        // Leerer Konstruktor
    }

    // Voller Konstruktor
    public Customer(final UUID id, final String vorname, final String nachname,
                    final String email, final LocalDate geburtsdatum) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.geburtsdatum = geburtsdatum;
    }

    // --- Core Java Methoden ---

    @Override
    public boolean equals(final Object other) {
        // equals/hashCode basieren auf der ID
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
            ", vorname='" + vorname + '\'' +
            ", nachname='" + nachname + '\'' +
            ", email='" + email + '\'' +
            ", geburtsdatum=" + geburtsdatum +
            '}';
    }

    // --- Getter und Setter ---

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(final String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(final String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(final LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }
}
