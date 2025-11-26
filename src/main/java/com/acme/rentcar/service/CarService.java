/*
 * Copyright (C) 2025 - present Juergen Zimmermann, Hochschule Karlsruhe
 * ...
 */
package com.acme.rentcar.service;

import com.acme.rentcar.controller.CarDTO;
import com.acme.rentcar.entity.Car;
import com.acme.rentcar.entity.CarDetails;
import com.acme.rentcar.repository.CarRepository;
import java.lang.StableValue; // Java 25 Preview
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/// Service-Schicht für Autos.

@Service
@SuppressWarnings("preview")
public final class CarService {

    private static final StableValue<Logger> LOGGER = StableValue.of();

    static {
        LOGGER.setOrThrow(LoggerFactory.getLogger(CarService.class));
    }

    private final CarRepository repository;

    CarService(final CarRepository repository) {
        this.repository = repository;
    }

    public Collection<Car> findAll() {
        final var cars = repository.findAll();
        LOGGER.orElseThrow().debug("findAll: {} Autos gefunden", cars.size());
        return cars;
    }


    public Car findById(final UUID id) {
        final var car = repository.findById(id);
        if (car == null) {
            LOGGER.orElseThrow().warn("findById: Auto mit ID {} nicht gefunden", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found with ID: " + id);
        }
        return car;
    }

    public Collection<Car> findByHersteller(final String hersteller) {
        return repository.findByHersteller(hersteller);
    }

    public Car create(final CarDTO dto) {
        LOGGER.orElseThrow().debug("create: Transformiere DTO zu Entity: {}", dto);
        final var newCar = new Car();
        newCar.setId(UUID.randomUUID());
        newCar.setHersteller(dto.hersteller());
        newCar.setModell(dto.modell());
        newCar.setKennzeichen(dto.kennzeichen());
        newCar.setErstzulassung(dto.erstzulassung());

        final var details = new CarDetails(
            UUID.randomUUID(),
            dto.farbe(),
            dto.sitzplaetze(),
            dto.motor(),
            Year.of(dto.erstzulassung().getYear())
        );
        newCar.setDetails(details);
        newCar.setRentals(List.of());

        final var savedCar = repository.save(newCar);
        LOGGER.orElseThrow().info("create: Auto erfolgreich gespeichert: {}", savedCar.getId());
        return savedCar;
    }

    public Car update(final UUID id, final CarDTO dto) {
        LOGGER.orElseThrow().debug("update: Aktualisiere Auto {}", id);

        // 1. Prüfen ob vorhanden
        final var existingCar = findById(id);

        // 2. Mapping
        existingCar.setHersteller(dto.hersteller());
        existingCar.setModell(dto.modell());
        existingCar.setKennzeichen(dto.kennzeichen());
        existingCar.setErstzulassung(dto.erstzulassung());

        final var details = existingCar.getDetails();
        details.setFarbe(dto.farbe());
        details.setSitzplaetze(dto.sitzplaetze());
        details.setMotor(dto.motor());
        details.setBaujahr(Year.of(dto.erstzulassung().getYear()));

        // 3. Update (Repository gibt @Nullable zurück, aber wir wissen es existiert)
        repository.update(existingCar);
        return existingCar;
    }
}
