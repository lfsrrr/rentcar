package com.acme.rentcar.controller;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.service.CarService;
import jakarta.validation.Valid; // NEU: Import für Validierung
import java.net.URI; // NEU: Import für Location Header
import java.util.Collection;
import java.util.UUID;
import org.springframework.http.ResponseEntity; // NEU: Import für HTTP-Antworten
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // NEU: Import für POST
import org.springframework.web.bind.annotation.PutMapping; // NEU: Import für PUT
import org.springframework.web.bind.annotation.RequestBody; // NEU: Import für Request Body
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // NEU

@RestController
@RequestMapping(CarController.API_PATH)
// Konvention: Controller MUSS public sein, um von Spring/Tests korrekt erkannt zu werden
public final class CarController {

    public static final String API_PATH = "/cars";
    private final CarService service;

    // Konvention: Konstruktor Package-Private
    CarController(final CarService service) {
        this.service = service;
    }

    // --- GET-Methoden (unverändert) ---
    @GetMapping("/all")
    public Collection<Car> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Car getById(@PathVariable final UUID id) {
        return service.findById(id);
    }

    @GetMapping
    public Collection<Car> getByQuery(@RequestParam(required = false) final String hersteller) {
        if (hersteller == null) {
            return service.findAll();
        }
        return service.findByHersteller(hersteller);
    }

    // --- NEU: POST (Anlegen) ---
    @PostMapping
    public ResponseEntity<Void> createCar(@RequestBody @Valid final CarWriteDTO carDto) {
        final Car newCar = service.create(carDto);

        final URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newCar.getId())
            .toUri();

        // Sendet HTTP 201 (Created) mit der Location des neuen Objekts
        return ResponseEntity.created(location).build();
    }

    // --- NEU: PUT (Ändern) ---
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(
        @PathVariable final UUID id,
        @RequestBody @Valid final CarWriteDTO carDto
    ) {
        final Car updatedCar = service.update(id, carDto);
        // Sendet HTTP 200 (OK) mit dem aktualisierten Objekt
        return ResponseEntity.ok(updatedCar);
    }
}
