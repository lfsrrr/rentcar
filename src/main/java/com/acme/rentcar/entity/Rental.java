package com.acme.rentcar.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

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

/// Repräsentiert einen konkreten Mietvorgang (Buchung) in der Datenbank.
///
/// Ein Rental verknüpft einen Kunden ([Customer]) mit einem Fahrzeug (referenziert durch `carId`)
/// für einen bestimmten Zeitraum.
///
/// Technische Hinweise:
/// - Die Beziehung zum Auto ist hier lose über die UUID (`carId`) gekoppelt,
///   während der Kunde als direkte JPA-Relation (`@ManyToOne`) eingebunden ist.
/// - Nutzt [AuditingEntityListener] zur automatischen Erfassung von Erstellungs- und Änderungszeiten.
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Rental {

    /// Eindeutige ID des Mietvorgangs (Primary Key).
    @Id
    @GeneratedValue
    @Nullable
    private UUID id;

    /// Versionsnummer für Optimistic Locking.
    /// Verhindert Datenkonflikte bei gleichzeitigen Änderungen.
    @Version
    private int version;

    /// Das Datum, an dem die Miete beginnt.
    private LocalDate mietbeginn;

    /// Das Datum, an dem die Miete endet (Rückgabe).
    private LocalDate mietende;

    /// Der berechnete Gesamtpreis für den Mietzeitraum.
    private BigDecimal gesamtpreis;

    /// Der Kunde, der das Fahrzeug gemietet hat.
    /// Es handelt sich um eine N:1 Beziehung (Ein Kunde kann mehrere Mieten haben).
    @ManyToOne
    @Nullable
    private Customer customer;

    /// Die ID des gemieteten Autos.
    ///
    /// Hinweis: Wir speichern hier nur die ID als Referenz, statt einer `@ManyToOne`-Beziehung
    /// zur [Car]-Klasse, um zyklische Abhängigkeiten zu minimieren oder weil die Navigation
    /// meist vom Auto ausgeht (`Car` -> `rentals`).
    @Nullable
    private UUID carId;

    /// Zeitstempel der Erstellung des Datensatzes.
    @CreatedDate
    @Nullable
    private LocalDateTime created;

    /// Zeitstempel der letzten Aktualisierung.
    @LastModifiedDate
    @Nullable
    private LocalDateTime updated;

    /// JPA-Standardkonstruktor.
    @SuppressWarnings("NullAway.Init")
    public Rental() {
    }

    /// Erstellt einen neuen Mietvorgang.
    ///
    /// @param id          Die ID (meist `null` beim Erstellen).
    /// @param mietbeginn  Startdatum.
    /// @param mietende    Enddatum.
    /// @param gesamtpreis Preis der Buchung.
    /// @param customer    Der zugehörige Kunde.
    /// @param carId       Die ID des Autos.
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

    /// Liefert die eindeutige ID des Mietvorgangs.
    ///
    /// @return Die UUID oder `null`, falls noch nicht persistiert.
    @Nullable
    public UUID getId() {
        return id; }

    /// Setzt die ID des Mietvorgangs.
    ///
    /// @param id Die neue UUID.
    public void setId(@Nullable final UUID id) {
        this.id = id; }

    /// Liefert die Versionsnummer.
    ///
    /// @return Die aktuelle Version (für Optimistic Locking).
    public int getVersion() {
        return version; }

    /// Setzt die Versionsnummer.
    ///
    /// @param version Die neue Versionsnummer.
    public void setVersion(final int version) {
        this.version = version; }

    /// Liefert das Datum des Mietbeginns.
    ///
    /// @return Das Startdatum.
    public LocalDate getMietbeginn() {
        return mietbeginn; }

    /// Setzt den Mietbeginn.
    ///
    /// @param mietbeginn Das neue Startdatum.
    public void setMietbeginn(final LocalDate mietbeginn) {
        this.mietbeginn = mietbeginn; }

    /// Liefert das Datum des Mietendes.
    ///
    /// @return Das Rückgabedatum.
    public LocalDate getMietende() {
        return mietende; }

    /// Setzt das Mietende.
    ///
    /// @param mietende Das neue Rückgabedatum.
    public void setMietende(final LocalDate mietende) {
        this.mietende = mietende; }

    /// Liefert den Gesamtpreis.
    ///
    /// @return Der Preis als BigDecimal.
    public BigDecimal getGesamtpreis() {
        return gesamtpreis; }

    /// Setzt den Gesamtpreis.
    ///
    /// @param gesamtpreis Der neue Preis.
    public void setGesamtpreis(final BigDecimal gesamtpreis) {
        this.gesamtpreis = gesamtpreis; }

    /// Liefert den zugehörigen Kunden.
    ///
    /// @return Das Customer-Objekt oder `null`.
    @Nullable
    public Customer getCustomer() {
        return customer; }

    /// Ordnet einen Kunden zu.
    ///
    /// @param customer Der Kunde.
    public void setCustomer(@Nullable final Customer customer) {
        this.customer = customer; }

    /// Liefert die ID des gemieteten Autos.
    ///
    /// @return Die UUID des Autos oder `null`.
    @Nullable
    public UUID getCarId() {
        return carId; }

    /// Setzt die ID des Autos.
    ///
    /// @param carId Die UUID des Fahrzeugs.
    public void setCarId(@Nullable final UUID carId) {
        this.carId = carId; }

    /// Liefert das Erstellungsdatum.
    ///
    /// @return Der Zeitpunkt der Erstellung.
    @Nullable
    public LocalDateTime getCreated() {
        return created; }

    /// Setzt das Erstellungsdatum.
    ///
    /// @param created Der neue Zeitpunkt.
    public void setCreated(@Nullable final LocalDateTime created) {
        this.created = created; }

    /// Liefert das Änderungsdatum.
    ///
    /// @return Der Zeitpunkt der letzten Änderung.
    @Nullable
    public LocalDateTime getUpdated() {
        return updated; }

    /// Setzt das Änderungsdatum.
    ///
    /// @param updated Der neue Zeitpunkt.
    public void setUpdated(@Nullable final LocalDateTime updated) {
        this.updated = updated; }
}
