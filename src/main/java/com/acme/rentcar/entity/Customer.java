package com.acme.rentcar.entity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

///
/// Kunde der Autovermietung.
///
@SuppressWarnings("checkstyle:HiddenField")
public class Customer {

    private UUID id;
    private String vorname;
    private String nachname;
    private String email;
    private LocalDate geburtsdatum;

    /// Initialisiert das Customer-Objekt (Standardkonstruktor).
    @SuppressWarnings("NullAway.Init")
    public Customer() {

    }

    /// Erstellt ein Customer-Objekt.
    ///
    /// @param id Die eindeutige ID des Kunden.
    /// @param vorname Der Vorname des Kunden.
    /// @param nachname Der Nachname des Kunden.
    /// @param email Die E-Mail-Adresse des Kunden.
    /// @param geburtsdatum Das Geburtsdatum des Kunden.
    public Customer(final UUID id, final String vorname, final String nachname,
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
            ", vorname='" + vorname + '\'' +
            ", nachname='" + nachname + '\'' +
            ", email='" + email + '\'' +
            ", geburtsdatum=" + geburtsdatum +
            '}';
    }

    /// Gibt die ID zurück.
    /// @return Die ID.
    public UUID getId() {
        return id;
    }

    /// Setzt die ID.
    /// @param id Die neue ID.
    public void setId(final UUID id) {
        this.id = id;
    }

    /// Gibt den Vornamen zurück.
    /// @return Der Vorname.
    public String getVorname() {
        return vorname;
    }

    /// Setzt den Vornamen.
    /// @param vorname Der neue Vorname.
    public void setVorname(final String vorname) {
        this.vorname = vorname;
    }

    /// Gibt den Nachnamen zurück.
    /// @return Der Nachname.
    public String getNachname() {
        return nachname;
    }

    /// Setzt den Nachnamen.
    /// @param nachname Der neue Nachname.
    public void setNachnamen(final String nachname) {
        this.nachname = nachname;
    }

    /// Gibt die E-Mail-Adresse zurück.
    /// @return Die E-Mail-Adresse.
    public String getEmail() {
        return email;
    }

    /// Setzt die E-Mail-Adresse.
    /// @param email Die neue E-Mail-Adresse.
    public void setEmail(final String email) {
        this.email = email;
    }

    /// Gibt das Geburtsdatum zurück.
    /// @return Das Geburtsdatum.
    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    /// Setzt das Geburtsdatum.
    /// @param geburtsdatum Das neue Geburtsdatum.
    public void setGeburtsdatum(final LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }
}
