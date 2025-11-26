package com.acme.rentcar.controller;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.UUID;
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

@RestController
@RequestMapping(CarController.API_PATH)
public final class CarController {

    public static final String API_PATH = "/cars";
    private final CarService service;

    CarController(final CarService service) {
        this.service = service;
    }

    @Operation(summary = "Get users",
        description = "Get list of users")
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

    @PostMapping
    public ResponseEntity<Void> createCar(@RequestBody @Valid final CarWriteDTO carDto) {
        final Car newCar = service.create(carDto);

        final URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newCar.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(
        @PathVariable final UUID id,
        @RequestBody @Valid final CarWriteDTO carDto
    ) {
        final Car updatedCar = service.update(id, carDto);

        return ResponseEntity.ok(updatedCar);
    }
}
