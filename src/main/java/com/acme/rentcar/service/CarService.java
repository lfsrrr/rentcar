package com.acme.rentcar.service;

import com.acme.rentcar.controller.CarDTO;
import com.acme.rentcar.controller.CarMapper;
import com.acme.rentcar.entity.Car;
import com.acme.rentcar.entity.CarDetails;
import com.acme.rentcar.repository.CarRepository;
import com.acme.rentcar.repository.CarSpecifications;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@SuppressWarnings("preview")
public class CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private final CarRepository repository;
    private final CarMapper mapper;

    public CarService(final CarRepository repository, final CarMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /// Sucht Autos mit dynamischen Kriterien (Specification).
    @Transactional(readOnly = true)
    public Collection<CarDTO> find(final MultiValueMap<String, String> searchParams) {
        final Specification<Car> spec = CarSpecifications.withCriteria(searchParams);

        // Suche mit Specification und Sortierung
        final var cars = repository.findAll(spec, Sort.by("hersteller").ascending());

        LOGGER.debug("find: {} Autos gefunden", cars.size());
        return mapper.toDTOs(cars);
    }

    @Transactional(readOnly = true)
    public CarDTO findById(final UUID id) {
        final var car = repository.findCarById(id);
        if (car == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }
        return mapper.toDTO(car);
    }

    public CarDTO create(final CarDTO dto) {
        final var newCar = new Car(null, dto.hersteller(), dto.modell(), dto.erstzulassung(),
            dto.kennzeichen(), null, List.of());

        final var details = new CarDetails(null, dto.farbe(), dto.sitzplaetze(),
            dto.motor(), Year.of(dto.erstzulassung().getYear()));

        newCar.setDetails(details);
        final var savedCar = repository.save(newCar);

        return mapper.toDTO(savedCar);
    }

    public CarDTO update(final UUID id, final CarDTO dto) {
        final var existingCar = repository.findCarById(id);
        if (existingCar == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }

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

        final var savedCar = repository.save(existingCar);
        return mapper.toDTO(savedCar);
    }
}
