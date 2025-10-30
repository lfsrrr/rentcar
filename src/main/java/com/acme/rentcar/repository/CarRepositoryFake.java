package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.entity.CarDetails;
import com.acme.rentcar.entity.Customer;
import com.acme.rentcar.entity.EngineType;
import com.acme.rentcar.entity.Rental;
import java.time.LocalDate;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

/**
 * Stellt eine Fake-Implementierung des CarRepository für den Meilenstein 5 bereit.
 * Simuliert Datenbankzugriff mit fest codierten ReadOnly-Daten.
 */
@Repository // Markiert diese Klasse als Spring Repository Komponente
public class CarRepositoryFake implements CarRepository {

    // Statische Collection für die simulierten Car-Daten.
    private static final Collection<Car> FAKE_CARS = List.of(
        createTestCar(
            UUID.fromString("c0714b62-9e9f-43b6-905c-d5f9d14620f1"),
            "BMW", "M4", "KA-MA-11", EngineType.BENZIN, "Gelb", 2023,
            "Max", "Mustermann", "max@hka.de"
        ),
        createTestCar(
            UUID.fromString("7302f354-1b15-408a-b83b-f542d9c41d08"),
            "Tesla", "Model S", "HD-TS-22", EngineType.ELEKTRO, "Weiß", 2024,
            "Lena", "Meyer", "lena@hka.de"
        )
    );

    @Override
    public Collection<Car> findAll() {
        // Implementiert die erforderliche ReadOnly-Methode für den Meilenstein.
        return FAKE_CARS;
    }

    // --- Hilfsmethode zur Erstellung der Daten und Abbildung der Beziehungen ---

    private static Car createTestCar(
        final UUID carId, final String hersteller, final String modell, final String kennzeichen,
        final EngineType engine, final String farbe, final int baujahr,
        final String kundenVorname, final String kundenNachname, final String kundenEmail) {

        final UUID customerId = UUID.randomUUID();
        final UUID rentalId = UUID.randomUUID();

        // 1. Erstellung der CarDetails (1:1 Beziehung)
        final CarDetails details = new CarDetails(
            UUID.randomUUID(),
            farbe,
            5,
            engine,
            Year.of(baujahr)
        );

        // 2. Erstellung des Customer (Ziel der 1:1 Beziehung von Rental)
        final Customer customer = new Customer(
            customerId,
            kundenVorname,
            kundenNachname,
            kundenEmail,
            LocalDate.of(1990, 1, 1)
        );

        // 3. Erstellung des Rental-Vorgangs (N-Seite der 1:N Beziehung zu Car)
        // Rental besitzt die gerichteten 1:1 und N:1-Beziehungen (Customer und CarId).
        final Rental rental = new Rental(
            rentalId,
            LocalDate.now().minusDays(10),
            LocalDate.now().minusDays(5),
            550.00,
            customer, // 1:1 Beziehung (Customer wird hier eingebettet/referenziert)
            carId     // N:1 Beziehung (Fremdschlüssel zum Car)
        );

        // 4. Erstellung des Car-Objekts (1-Seite der 1:N Beziehung)
        // Car referenziert Rental über eine Liste.
        return new Car(
            carId,
            hersteller,
            modell,
            LocalDate.of(baujahr, 1, 1),
            kennzeichen,
            details,      // 1:1 Beziehung
            List.of(rental) // 1:N Beziehung (Liste von Rentals)
        );
    }
}
