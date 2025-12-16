package com.acme.rentcar.controller;

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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @Operation(summary = "Autos suchen / filtern")
    @GetMapping
    Collection<CarDTO> get(@RequestParam(required = false) final MultiValueMap<String, String> searchParams) {
        LOGGER.orElseThrow().debug("GET /cars mit params: {}", searchParams);
        return service.find(searchParams);
    }

    @Operation(summary = "Auto per ID suchen")
    @ApiResponse(responseCode = "200", description = "Auto gefunden")
    @ApiResponse(responseCode = "404", description = "Auto nicht gefunden")
    @GetMapping("/{id}")
    ResponseEntity<CarDTO> getById(@PathVariable final UUID id) {
        final var carDto = service.findById(id);
        return ResponseEntity.ok(carDto);
    }

    @Operation(summary = "Neues Auto anlegen")
    @ApiResponse(responseCode = "201", description = "Erfolgreich angelegt")
    @PostMapping
    ResponseEntity<Void> createCar(@RequestBody @Valid final CarDTO carDto) {
        final var createdCar = service.create(carDto);
        final var location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdCar.id()) // Zugriff auf ID im Record ist id(), nicht getId()
            .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Auto aktualisieren")
    @PutMapping("/{id}")
    ResponseEntity<CarDTO> updateCar(
        @PathVariable final UUID id,
        @RequestBody @Valid final CarDTO carDto
    ) {
        final var updatedCar = service.update(id, carDto);
        return ResponseEntity.ok(updatedCar);
    }
}
