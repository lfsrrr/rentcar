package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.entity.CarDetails;
import com.acme.rentcar.entity.Customer;
import com.acme.rentcar.entity.EngineType;
import com.acme.rentcar.entity.Rental;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

/// Mock-Repository
@Repository
@SuppressWarnings("checkstyle:ParameterNumber")
final class CarRepositoryFake implements CarRepository {

    private static final List<Car> FAKE_CARS = new ArrayList<>(List.of(
        createTestCar(
            UUID.fromString("c0714b62-9e9f-43b6-905c-d5f9d14620f1"),
            "BMW", "M4", "KA-MA-11", EngineType.BENZIN, "Gelb", 2023,
            "Max", "Mustermann", "max@hka.de"
        ),
        // ... (Ihre weiteren Autos hier lassen oder einfügen) ...
        createTestCar(
            UUID.fromString("7302f354-1b15-408a-b83b-f542d9c41d08"),
            "Tesla", "Model S", "HD-TS-22", EngineType.ELEKTRO, "Weiß", 2024,
            "Lena", "Meyer", "lena@hka.de"
        )
    ));

    @Override
    public Collection<Car> findAll() {
        return FAKE_CARS;
    }

    @Override
    public @Nullable Car findById(final UUID id) {
        return FAKE_CARS.stream()
            .filter(car -> car.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public Collection<Car> findByHersteller(final String hersteller) {
        return FAKE_CARS.stream()
            .filter(car -> car.getHersteller().equalsIgnoreCase(hersteller))
            .collect(Collectors.toList());
    }

    @Override
    public Car save(final Car car) {
        FAKE_CARS.add(car);
        return car;
    }

    @Override
    public @Nullable Car update(final Car car) {
        final var existingCar = findById(car.getId());
        if (existingCar == null) {
            return null;
        }

        FAKE_CARS.remove(existingCar);
        FAKE_CARS.add(car);
        return car;
    }

    @SuppressWarnings("checkstyle:ParameterNumber")
    private static Car createTestCar(
        final UUID carId, final String hersteller, final String modell, final String kennzeichen,
        final EngineType engine, final String farbe, final int baujahr,
        final String kundenVorname, final String kundenNachname, final String kundenEmail) {

        final UUID customerId = UUID.randomUUID();
        final UUID rentalId = UUID.randomUUID();

        final CarDetails details = new CarDetails(
            UUID.randomUUID(),
            farbe,
            "911".equals(modell) ? 2 : 5,
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
