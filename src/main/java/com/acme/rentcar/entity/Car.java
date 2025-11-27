package com.acme.rentcar.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

///
/// Car entity ist das Auto der Autovermietung.
///
public class Car {
    private UUID id;
    private String hersteller;
    private String modell;
    private LocalDate erstzulassung;
    private String kennzeichen;
    private CarDetails details;
    private List<Rental> rentals;

    ///  Standard Konstruktor für Frameworks
    @SuppressWarnings("NullAway.Init")
    public Car() {

    }

    /// Erstellt ein neues Auto.
    ///
    /// @param id Die eindeutige ID des Autos.
    /// @param hersteller Der Name des Herstellers (z.B. BMW).
    /// @param modell Das Modellbezeichnung.
    /// @param erstzulassung Datum der ersten Zulassung.
    /// @param kennzeichen Das amtliche Kennzeichen.
    /// @param details Referenz auf die Details-Entity.
    /// @param rentals Liste der zugehörigen Mietverträge.
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

    /// Gibt die UUID des Autos zurück.
    /// @return Die UUID.
    public UUID getId() {
        return id;
    }

    /// Setzt die ID des Autos.
    /// @param id Die neue UUID.
    public void setId(final UUID id) {
        this.id = id;
    }

    /// Gibt den Hersteller zurück.
    /// @return hersteller
    public String getHersteller() {
        return hersteller;
    }

    /// Setzt den Hersteller.
    /// @param hersteller Der neue Hersteller.
    public void setHersteller(final String hersteller) {
        this.hersteller = hersteller;
    }

    /// Gibt das Modell zurück
    /// @return Modell.
    public String getModell() {
        return modell;
    }

    /// Setzt das Modell.
    /// @param modell Das neue Modell.
    public void setModell(final String modell) {
        this.modell = modell;
    }

    /// Gibt Erstzulassung zurück.
    /// @return erstzulassung.
    public LocalDate getErstzulassung() {
        return erstzulassung;
    }

    /// Setzt Erstzulassung.
    /// @param erstzulassung Das neue Datum.
    public void setErstzulassung(final LocalDate erstzulassung) {
        this.erstzulassung = erstzulassung;
    }

    /// Gibt Kennzeichen zurück.
    /// @return kennzeichen
    public String getKennzeichen() {
        return kennzeichen;
    }

    /// Setzt Kennzeichen.
    /// @param kennzeichen Das neue Kennzeichen.
    public void setKennzeichen(final String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    /// Gibt Details zurück.
    /// @return details
    public CarDetails getDetails() {
        return details;
    }

    /// Setzt Details.
    /// @param details Die neuen Details.
    public void setDetails(final CarDetails details) {
        this.details = details;
    }

    /// Gibt die Liste der Mietvorgänge zurück.
    /// @return Liste von Rental-Objekten
    public List<Rental> getRentals() {
        return rentals;
    }

    /// Setzt die Liste von Mietvorgängen.
    /// @param rentals Die neue Liste.
    public void setRentals(final List<Rental> rentals) {
        this.rentals = rentals;
    }
}
