package com.acme.rentcar.controller;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/// REST-Controller für die Verwaltung von Autos.
///
/// Stellt HTTP-Endpunkte bereit, um Autos zu suchen, neu anzulegen
/// oder bestehende Einträge zu aktualisieren.
@RestController
@RequestMapping("/cars")
@SuppressWarnings("preview")
final class CarController {

    /// Logger-Instanz, gehalten in einem StableValue (Java 25 Preview Feature).
    private static final StableValue<Logger> LOGGER = StableValue.of();

    static {
        LOGGER.setOrThrow(LoggerFactory.getLogger(CarController.class));
    }

    private final CarService service;

    /// Erstellt den Controller und injiziert den benötigten Service.
    ///
    /// @param service Die Business-Logik-Komponente für Autos.
    CarController(final CarService service) {
        this.service = service;
    }

    /// Ruft eine Liste aller verfügbaren Autos ab.
    ///
    /// @return Eine Collection aller Car Instanzen.
    @Operation(summary = "Alle Autos abrufen")
    @GetMapping("/all")
    Collection<Car> getAll() {
        LOGGER.orElseThrow().debug("GET /all");
        return service.findAll();
    }

    /// Sucht ein spezifisches Auto anhand seiner eindeutigen ID.
    ///
    /// @param id Die UUID des gesuchten Autos.
    /// @return Das gefundene Auto.
    @Operation(summary = "Auto per ID suchen")
    @ApiResponse(responseCode = "200", description = "Auto gefunden")
    @ApiResponse(responseCode = "404", description = "Auto nicht gefunden")
    @GetMapping("/{id}")
    Car getById(@PathVariable final UUID id) {
        return service.findById(id);
    }

    /// Sucht Autos basierend auf optionalen Filterkriterien.
    ///
    /// Wenn kein Parameter übergeben wird, verhält sich diese Methode wie getAll.
    ///
    /// @param hersteller Optionaler Name des Herstellers zur Filterung.
    /// @return Eine Collection der passenden Autos.
    @Operation(summary = "Autos filtern (z.B. nach Hersteller)")
    @GetMapping
    Collection<Car> getByQuery(@RequestParam(required = false) final String hersteller) {
        if (hersteller == null) {
            return service.findAll();
        }
        return service.findByHersteller(hersteller);
    }

    /// Legt ein neues Auto basierend auf den übergebenen Daten an.
    ///
    /// Validiert das DTO und erstellt bei Erfolg einen Location-Header.
    ///
    /// @param carDto Das Datentransferobjekt mit den Auto-Informationen.
    /// @return Ein ResponseEntity ohne Body, aber mit Location-Header zur neuen Ressource.
    @Operation(summary = "Neues Auto anlegen")
    @ApiResponse(responseCode = "201", description = "Erfolgreich angelegt")
    @PostMapping
    ResponseEntity<Void> createCar(@RequestBody @Valid final CarDTO carDto) {
        final var newCar = service.create(carDto);
        final var location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newCar.getId())
            .toUri();
        return ResponseEntity.created(location).build();
    }

    /// Aktualisiert die Daten eines existierenden Autos.
    ///
    /// @param id Die UUID des zu aktualisierenden Autos.
    /// @param carDto Die neuen Daten für das Auto.
    /// @return Ein ResponseEntity, das das aktualisierte Auto enthält.
    @Operation(summary = "Auto aktualisieren")
    @PutMapping("/{id}")
    ResponseEntity<Car> updateCar(
        @PathVariable final UUID id,
        @RequestBody @Valid final CarDTO carDto
    ) {
        final var updatedCar = service.update(id, carDto);
        return ResponseEntity.ok(updatedCar);
    }
}
