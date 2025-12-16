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

/// Service-Klasse für die Verwaltung von Autos.
///
/// Diese Komponente kapselt die Geschäftslogik und die Transaktionssteuerung.
/// Sie nimmt Anfragen vom Controller entgegen, interagiert mit dem [CarRepository]
/// und konvertiert die Ergebnisse mittels [CarMapper] in DTOs.
@Service
@Transactional
@SuppressWarnings("preview")
public class CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private final CarRepository repository;
    private final CarMapper mapper;

    /// Erstellt eine neue Instanz des Services.
    ///
    /// @param repository Das Repository für den Datenbankzugriff.
    /// @param mapper     Der Mapper für die Konvertierung zwischen Entity und DTO.
    public CarService(final CarRepository repository, final CarMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /// Sucht Autos basierend auf dynamischen Filterkriterien.
    ///
    /// Die Filter werden über Query-Parameter (z.B. `?hersteller=BMW`) übergeben und
    /// mithilfe von JPA Specifications in eine Datenbankabfrage übersetzt.
    /// Das Ergebnis wird nach Hersteller aufsteigend sortiert.
    ///
    /// @param searchParams Eine Map der Query-Parameter aus der URL.
    /// @return Eine Liste der gefundenen Autos als [CarDTO].
    @Transactional(readOnly = true)
    public Collection<CarDTO> find(final MultiValueMap<String, String> searchParams) {
        final Specification<Car> spec = CarSpecifications.withCriteria(searchParams);

        // Suche mit Specification und Sortierung
        final var cars = repository.findAll(spec, Sort.by("hersteller").ascending());

        LOGGER.debug("find: {} Autos gefunden", cars.size());
        return mapper.toDTOs(cars);
    }

    /// Lädt ein einzelnes Auto anhand seiner eindeutigen ID.
    ///
    /// @param id Die UUID des gesuchten Autos.
    /// @return Das gefundene Auto als [CarDTO].
    /// @throws ResponseStatusException (404 Not Found), wenn kein Auto mit dieser ID existiert.
    @Transactional(readOnly = true)
    public CarDTO findById(final UUID id) {
        final var car = repository.findCarById(id);
        if (car == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }
        return mapper.toDTO(car);
    }

    /// Legt ein neues Auto inklusive technischer Details in der Datenbank an.
    ///
    /// Erstellt die [Car] und [CarDetails] Entities manuell aus dem übergebenen DTO,
    /// verknüpft diese und speichert sie persistiert ab.
    ///
    /// @param dto Das DTO mit den Daten des neuen Autos.
    /// @return Das gespeicherte Auto als DTO (inklusive der generierten ID).
    public CarDTO create(final CarDTO dto) {
        final var newCar = new Car(null, dto.hersteller(), dto.modell(), dto.erstzulassung(),
            dto.kennzeichen(), null, List.of());

        final var details = new CarDetails(null, dto.farbe(), dto.sitzplaetze(),
            dto.motor(), Year.of(dto.erstzulassung().getYear()));

        newCar.setDetails(details);
        final var savedCar = repository.save(newCar);

        return mapper.toDTO(savedCar);
    }

    /// Aktualisiert die Daten eines bestehenden Autos.
    ///
    /// Lädt das Auto aus der Datenbank und überschreibt die Felder mit den Werten aus dem DTO.
    /// Aktualisiert auch die verknüpften technischen Details ([CarDetails]).
    ///
    /// @param id  Die ID des zu aktualisierenden Autos.
    /// @param dto Die neuen Daten.
    /// @return Das aktualisierte Auto als DTO.
    /// @throws ResponseStatusException (404 Not Found), wenn das Auto nicht gefunden wurde.
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
