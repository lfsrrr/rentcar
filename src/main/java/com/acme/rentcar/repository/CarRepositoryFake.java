package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.entity.CarDetails;
import com.acme.rentcar.entity.Customer;
import com.acme.rentcar.entity.EngineType;
import com.acme.rentcar.entity.Rental;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Year;
import java.util.ArrayList; // Import für die veränderbare Liste
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * Mock Repo for testing.
 */
@Repository
// Konvention: Package-Private
public final class CarRepositoryFake implements CarRepository {

    // KORREKTUR: Muss eine veränderbare Liste sein (new ArrayList<>)
    private static final List<Car> FAKE_CARS = new ArrayList<>(List.of(
        createTestCar(
            UUID.fromString("c0714b62-9e9f-43b6-905c-d5f9d14620f1"),
            "BMW", "M4", "KA-MA-11", EngineType.BENZIN, "Gelb", 2023,
            "Max", "Mustermann", "max@hka.de"
        ),
        createTestCar(
            UUID.fromString("7302f354-1b15-408a-b83b-f542d9c41d08"),
            "Tesla", "Model S", "HD-TS-22", EngineType.ELEKTRO, "Weiß", 2024,
            "Lena", "Meyer", "lena@hka.de"
        ),
        createTestCar(
            UUID.fromString("a1b2c3d4-e5f6-a7b8-c9d0-e1f2a3b4c5d6"),
            "Porsche", "911", "S-P-911", EngineType.BENZIN, "Rot", 2025,
            "Tim", "Schulz", "tim@hka.de"
        ),
        createTestCar(
            UUID.fromString("b2c3d4e5-f6a7-b8c9-d0e1-f2a3b4c5d6e7"),
            "Volkswagen", "Golf", "MA-VW-04", EngineType.DIESEL, "Blau", 2022,
            "Anna", "Klein", "anna@hka.de"
        ),
        createTestCar(
            UUID.fromString("c3d4e5f6-a7b8-c9d0-e1f2-a3b4c5d6e7f8"),
            "Audi", "Q5", "B-AQ-55", EngineType.HYBRID, "Schwarz", 2024,
            "Paul", "Lang", "paul@hka.de"
        )
    ));

    @Override
    public Collection<Car> findAll() {
        return FAKE_CARS;
    }

    @Override
    public Optional<Car> findById(final UUID id) {
        return FAKE_CARS.stream()
            .filter(car -> car.getId().equals(id))
            .findFirst();
    }

    @Override
    public Collection<Car> findByHersteller(final String hersteller) {
        return FAKE_CARS.stream()
            .filter(car -> car.getHersteller().equalsIgnoreCase(hersteller))
            .collect(Collectors.toList());
    }

    // --- HIER BEGINNT DIE FEHLENDE IMPLEMENTIERUNG ---

    /**
     * {@inheritDoc}
     */
    @Override
    public Car save(final Car car) {
        FAKE_CARS.add(car);
        return car;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Car> update(final Car car) {
        // 1. Finde das alte Objekt
        // (Wir verwenden findById, das bereits Optional<Car> zurückgibt)
        final var oldCarOptional = findById(car.getId());

        // 2. KORREKTUR: Verwende orElseThrow statt .isEmpty() und .get()
        // Wir werfen hier eine IllegalStateException, da der Service
        // bereits sicherstellen sollte, dass das Auto existiert, bevor er update() aufruft.
        final var carToRemove = oldCarOptional.orElseThrow(
            () -> new IllegalStateException("Update fehlgeschlagen: Auto nicht in Fake-DB gefunden: " + car.getId())
        );

        // 3. Ersetze das Objekt in der (mutablen) Liste
        FAKE_CARS.remove(carToRemove);
        FAKE_CARS.add(car);
        return Optional.of(car);
    }

    private static Car createTestCar(
        final UUID carId, final String hersteller, final String modell, final String kennzeichen,
        final EngineType engine, final String farbe, final int baujahr,
        final String kundenVorname, final String kundenNachname, final String kundenEmail) {

        final UUID customerId = UUID.randomUUID();
        final UUID rentalId = UUID.randomUUID();

        final CarDetails details = new CarDetails(
            UUID.randomUUID(),
            farbe,
            (modell.equals("911") ? 2 : 5),
            engine,
            Year.of(baujahr)
        );

        final Customer customer = new Customer(
            customerId,
            kundenVorname,
            kundenNachname,
            kundenEmail,
            LocalDate.of(1990, 1, 1)
        );

        final Rental rental = new Rental(
            rentalId,
            LocalDate.now(ZoneId.systemDefault()).minusDays(10),
            LocalDate.now(ZoneId.systemDefault()).minusDays(5),
            550.00,
            customer,
            carId
        );

        return new Car(
            carId,
            hersteller,
            modell,
            LocalDate.of(baujahr, 1, 1),
            kennzeichen,
            details,
            List.of(rental)
        );
    }
}
