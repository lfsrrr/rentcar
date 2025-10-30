package com.acme.rentcar.entity; // Paketname auf rentcar.entity angepasst

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

// Annahmen für die Beziehungen (1:N):
// Ein Car kann viele Rental-Vorgänge (Mietvorgänge) haben.
// Ein Car hat eine CarDetails-Entität (1:1).

public class Car {
    private UUID id;
    private String hersteller; // Hersteller
    private String modell;     // Modell
    private LocalDate erstzulassung; // Entspricht Geburtsdatum
    private String kennzeichen;  // Kennzeichen

    // 1:1 Beziehung (Unidirektional: Car kennt Details)
    private CarDetails details;

    // 1:N Beziehung (Unidirektional: Car kennt seine Mieten)
    private List<Rental> rentals;

    // Optional: Leerer Konstruktor für JPA/Hibernate (kann später nötig sein)
    public Car() {
        // Leerer Konstruktor
    }

    // Voller Konstruktor (entspricht der Struktur des Patient-Beispiels)
    public Car(final UUID id, final String hersteller, final String modell, final LocalDate erstzulassung,
               final String kennzeichen, final CarDetails details, final List<Rental> rentals) {
        this.id = id;
        this.hersteller = hersteller;
        this.modell = modell;
        this.erstzulassung = erstzulassung;
        this.kennzeichen = kennzeichen;
        this.details = details;
        this.rentals = rentals;
    }

    // --- Core Java Methoden (Equals, HashCode, ToString) ---

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
            ", hersteller='" + hersteller + '\'' +
            ", modell='" + modell + '\'' +
            ", erstzulassung=" + erstzulassung +
            ", kennzeichen='" + kennzeichen + '\'' +
            ", details=" + details +
            ", rentals=" + rentals +
            '}';
    }

    // --- Getter und Setter (konventionell) ---
    // (Beachten Sie die sinnvolle Reihenfolge von Attributen und Funktionen)

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getHersteller() {
        return hersteller;
    }

    public void setHersteller(final String hersteller) {
        this.hersteller = hersteller;
    }

    public String getModell() {
        return modell;
    }

    public void setModell(final String modell) {
        this.modell = modell;
    }

    public LocalDate getErstzulassung() {
        return erstzulassung;
    }

    public void setErstzulassung(final LocalDate erstzulassung) {
        this.erstzulassung = erstzulassung;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(final String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public CarDetails getDetails() {
        return details;
    }

    public void setDetails(final CarDetails details) {
        this.details = details;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(final List<Rental> rentals) {
        this.rentals = rentals;
    }
}
