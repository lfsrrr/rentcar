package com.acme.rentcar.service;

import com.acme.rentcar.controller.CarWriteDTO; // NEU
import com.acme.rentcar.entity.Car;
import com.acme.rentcar.entity.CarDetails; // NEU
import com.acme.rentcar.repository.CarRepository;
import java.time.Year; // NEU
import java.util.Collection;
import java.util.List; // NEU
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
// Konvention: Service-Klasse MUSS public sein, damit Controller (anderes Paket) sie sieht
public final class CarService {

    private final CarRepository repository;

    // Konvention: Konstruktor Package-Private
    CarService(final CarRepository repository) {
        this.repository = repository;
    }

    // --- GET Methoden (unverändert) ---
    public Collection<Car> findAll() {
        return repository.findAll();
    }

    public Car findById(final UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Car not found with ID: " + id
            ));
    }

    public Collection<Car> findByHersteller(final String hersteller) {
        return repository.findByHersteller(hersteller);
    }

    // --- NEU: POST (Create) ---
    public Car create(final CarWriteDTO dto) {
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
        newCar.setRentals(List.of()); // Neue Autos haben keine Miet-Historie

        return repository.save(newCar);
    }

    // --- NEU: PUT (Update) ---
    public Car update(final UUID id, final CarWriteDTO dto) {
        final var existingCar = findById(id);

        existingCar.setHersteller(dto.hersteller());
        existingCar.setModell(dto.modell());
        existingCar.setKennzeichen(dto.kennzeichen());
        existingCar.setErstzulassung(dto.erstzulassung());

        final var details = existingCar.getDetails();
        details.setFarbe(dto.farbe());
        details.setSitzplaetze(dto.sitzplaetze());
        details.setMotor(dto.motor());
        details.setBaujahr(Year.of(dto.erstzulassung().getYear()));

        repository.update(existingCar);
        return existingCar;
    }
}
