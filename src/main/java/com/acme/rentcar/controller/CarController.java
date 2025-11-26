
package com.acme.rentcar.controller;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.lang.StableValue;
import java.util.Collection;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/cars")
@SuppressWarnings("preview")
final class CarController {

    private static final StableValue<Logger> LOGGER = StableValue.of();

    static {
        LOGGER.setOrThrow(LoggerFactory.getLogger(CarController.class));
    }

    private final CarService service;

    CarController(final CarService service) {
        this.service = service;
    }

    @Operation(summary = "Alle Autos abrufen")
    @GetMapping("/all")
    Collection<Car> getAll() {
        LOGGER.orElseThrow().debug("GET /all");
        return service.findAll();
    }

    @Operation(summary = "Auto per ID suchen")
    @ApiResponse(responseCode = "200", description = "Auto gefunden")
    @ApiResponse(responseCode = "404", description = "Auto nicht gefunden")
    @GetMapping("/{id}")
    Car getById(@PathVariable final UUID id) {
        return service.findById(id);
    }

    @Operation(summary = "Autos filtern (z.B. nach Hersteller)")
    @GetMapping
    Collection<Car> getByQuery(@RequestParam(required = false) final String hersteller) {
        if (hersteller == null) {
            return service.findAll();
        }
        return service.findByHersteller(hersteller);
    }

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
