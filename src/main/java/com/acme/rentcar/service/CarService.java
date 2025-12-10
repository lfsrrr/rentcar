package com.acme.rentcar.service;

import com.acme.rentcar.controller.CarDTO;
import com.acme.rentcar.entity.Car;
import com.acme.rentcar.entity.CarDetails;
import com.acme.rentcar.repository.CarRepository;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/// Service-Schicht für die Verwaltung von Autos.
@Service
@Transactional
@SuppressWarnings("preview")
public class CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);

    private final CarRepository repository;

    /// Erstellt den Service.
    ///
    /// @param repository Das Repository für den Datenzugriff.
    CarService(final CarRepository repository) {
        this.repository = repository;
    }

    /// Findet alle Autos.
    ///
    /// @return Eine Collection aller Autos.
    @Transactional(readOnly = true)
    public Collection<Car> findAll() {
        final var cars = repository.findAll();
        LOGGER.debug("findAll: {} Autos gefunden", cars.size());
        return cars;
    }

    /// Findet ein Auto per ID.
    ///
    /// @param id Die ID des Autos.
    /// @return Das gefundene Auto.
    /// @throws ResponseStatusException Wenn das Auto nicht gefunden wird (404).
    @Transactional(readOnly = true)
    public Car findById(final UUID id) {
        // Nutzt findCarById, das direkt Car (oder null) zurückgibt
        // Die Methode im Repository heißt jetzt findCarById, nicht findById!
        final var car = repository.findCarById(id);
        if (car == null) {
            LOGGER.warn("findById: Auto mit ID {} nicht gefunden", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found with ID: " + id);
        }
        return car;
    }

    /// Findet Autos nach Hersteller.
    ///
    /// @param hersteller Der Herstellername.
    /// @return Liste der passenden Autos.
    @Transactional(readOnly = true)
    public Collection<Car> findByHersteller(final String hersteller) {
        return repository.findByHerstellerIgnoreCase(hersteller);
    }

    /// Erstellt ein neues Auto aus einem DTO (POST-Logik).
    ///
    /// @param dto Die Eingabedaten.
    /// @return Das gespeicherte Auto.
    public Car create(final CarDTO dto) {
        LOGGER.debug("create: Transformiere DTO zu Entity: {}", dto);
        final var newCar = new Car();
        // ID wird durch @GeneratedValue in der Entity erzeugt

        newCar.setHersteller(dto.hersteller());
        newCar.setModell(dto.modell());
        newCar.setKennzeichen(dto.kennzeichen());
        newCar.setErstzulassung(dto.erstzulassung());

        final var details = new CarDetails(
            null, // ID wird generiert
            dto.farbe(),
            dto.sitzplaetze(),
            dto.motor(),
            Year.of(dto.erstzulassung().getYear())
        );
        newCar.setDetails(details);
        newCar.setRentals(List.of());

        final var savedCar = repository.save(newCar);
        LOGGER.info("create: Auto erfolgreich gespeichert: {}", savedCar.getId());
        return savedCar;
    }

    /// Aktualisiert ein Auto basierend auf dem DTO (PUT-Logik).
    ///
    /// @param id Die ID des zu aktualisierenden Autos.
    /// @param dto Die neuen Daten.
    /// @return Das aktualisierte Auto.
    /// @throws ResponseStatusException Wenn das Auto nicht gefunden wird (404).
    public Car update(final UUID id, final CarDTO dto) {
        LOGGER.debug("update: Aktualisiere Auto {}", id);

        // 1. Prüfen ob vorhanden (findById wirft Exception bei null)
        final var existingCar = findById(id);

        // 2. Mapping
        existingCar.setHersteller(dto.hersteller());
        existingCar.setModell(dto.modell());
        existingCar.setKennzeichen(dto.kennzeichen());
        existingCar.setErstzulassung(dto.erstzulassung());

        final var details = existingCar.getDetails();
        if (details != null) {
            details.setFarbe(dto.farbe());
            details.setSitzplaetze(dto.sitzplaetze());
            details.setMotor(dto.motor());
            details.setBaujahr(Year.of(dto.erstzulassung().getYear()));
        }

        // 3. Update im Repository
        return repository.save(existingCar);
    }
}
