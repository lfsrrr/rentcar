package com.acme.rentcar.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/// Car Entity repräsentiert ein Auto in der Datenbank.
///
/// Diese Klasse ist eine JPA-Entity, die auf die entsprechende Datenbanktabelle gemappt wird.
/// Sie speichert alle relevanten Informationen zu einem Fahrzeug, einschließlich technischer
/// Details und Mietvorgängen.
@Entity
@SuppressWarnings("PMD.ShortClassName")
public class Car {

    /// Die eindeutige ID des Autos.
    ///
    /// Wird automatisch von der Datenbank oder dem Persistence-Provider generiert.
    @Id
    @GeneratedValue
    private UUID id;

    /// Versionsnummer für optimistisches Locking.
    ///
    /// Dient dazu, gleichzeitige Änderungen (Lost Updates) zu verhindern.
    /// Wird von JPA automatisch verwaltet.
    @Version
    private int version;

    /// Der Name des Herstellers (z.B. BMW, Volkswagen).
    private String hersteller;

    /// Die Modellbezeichnung des Fahrzeugs (z.B. Golf 8, M4).
    private String modell;

    /// Das Datum der Erstzulassung des Fahrzeugs.
    private LocalDate erstzulassung;

    /// Das amtliche Kennzeichen des Fahrzeugs (z.B. KA-AB-123).
    private String kennzeichen;

    /// Technische Details zum Auto (1:1 Beziehung).
    ///
    /// Die Details werden in einer separaten Entity (`CarDetails`) gespeichert.
    /// `CascadeType.ALL` sorgt dafür, dass Änderungen an `Car` (z.B. Löschen)
    /// auch auf `CarDetails` übertragen werden.
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private CarDetails details;

    /// Liste der Mietvorgänge für dieses Auto (1:N Beziehung).
    ///
    /// Ein Auto kann mehrere Mietvorgänge haben. Auch hier werden Änderungen
    /// kaskadiert.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals;

    /// Standard-Konstruktor für JPA.
    ///
    /// Dieser Konstruktor ist notwendig, damit JPA-Implementierungen (wie Hibernate)
    /// Instanzen dieser Klasse per Reflection erstellen können.
    @SuppressWarnings("NullAway.Init")
    public Car() {
    }

    /// Erstellt ein neues Auto mit allen Eigenschaften.
    ///
    /// @param id Die eindeutige ID des Autos (kann null sein, wenn sie generiert wird).
    /// @param hersteller Der Name des Herstellers.
    /// @param modell Die Modellbezeichnung.
    /// @param erstzulassung Datum der ersten Zulassung.
    /// @param kennzeichen Das amtliche Kennzeichen.
    /// @param details Referenz auf die technischen Details.
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

    /// Überprüft die Gleichheit zweier Car-Objekte basierend auf der ID.
    ///
    /// @param other Das zu vergleichende Objekt.
    /// @return `true`, wenn die Objekte identisch sind (gleiche ID), sonst `false`.
    @Override
    public boolean equals(final Object other) {
        return other instanceof Car car && Objects.equals(id, car.id);
    }

    /// Berechnet den Hash-Code basierend auf der ID.
    ///
    /// @return Der Hash-Code.
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /// Gibt eine String-Repräsentation des Autos zurück.
    ///
    /// @return Ein String mit den wichtigsten Eigenschaften des Autos.
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
            '}';
    }

    // --- Getter und Setter ---

    /// Gibt die ID des Autos zurück.
    ///
    /// @return Die UUID.
    public UUID getId() {
        return id;
    }

    /// Setzt die ID des Autos.
    ///
    /// @param id Die neue UUID.
    public void setId(final UUID id) {
        this.id = id;
    }

    /// Gibt die Version des Datensatzes zurück.
    ///
    /// @return Die Versionsnummer.
    public int getVersion() {
        return version;
    }

    /// Setzt die Version (sollte i.d.R. nicht manuell gesetzt werden).
    ///
    /// @param version Die neue Version.
    public void setVersion(final int version) {
        this.version = version;
    }

    /// Gibt den Hersteller zurück.
    ///
    /// @return Der Herstellername.
    public String getHersteller() {
        return hersteller;
    }

    /// Setzt den Hersteller.
    ///
    /// @param hersteller Der neue Herstellername.
    public void setHersteller(final String hersteller) {
        this.hersteller = hersteller;
    }

    /// Gibt das Modell zurück.
    ///
    /// @return Die Modellbezeichnung.
    public String getModell() {
        return modell;
    }

    /// Setzt das Modell.
    ///
    /// @param modell Die neue Modellbezeichnung.
    public void setModell(final String modell) {
        this.modell = modell;
    }

    /// Gibt das Datum der Erstzulassung zurück.
    ///
    /// @return Das Erstzulassungsdatum.
    public LocalDate getErstzulassung() {
        return erstzulassung;
    }

    /// Setzt das Datum der Erstzulassung.
    ///
    /// @param erstzulassung Das neue Datum.
    public void setErstzulassung(final LocalDate erstzulassung) {
        this.erstzulassung = erstzulassung;
    }

    /// Gibt das Kennzeichen zurück.
    ///
    /// @return Das Kennzeichen.
    public String getKennzeichen() {
        return kennzeichen;
    }

    /// Setzt das Kennzeichen.
    ///
    /// @param kennzeichen Das neue Kennzeichen.
    public void setKennzeichen(final String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    /// Gibt die technischen Details zurück.
    ///
    /// @return Das `CarDetails`-Objekt.
    public CarDetails getDetails() {
        return details;
    }

    /// Setzt die technischen Details.
    ///
    /// @param details Das neue `CarDetails`-Objekt.
    public void setDetails(final CarDetails details) {
        this.details = details;
    }

    /// Gibt die Liste der Mietvorgänge zurück.
    ///
    /// @return Eine Liste von `Rental`-Objekten.
    public List<Rental> getRentals() {
        return rentals;
    }

    /// Setzt die Liste der Mietvorgänge.
    ///
    /// @param rentals Die neue Liste von Mietvorgängen.
    public void setRentals(final List<Rental> rentals) {
        this.rentals = rentals;
    }
}
