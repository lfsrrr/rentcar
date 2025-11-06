package com.acme.rentcar.controller;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.service.CarService;
import java.util.Collection;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(CarController.API_PATH)
final class CarController {

    static final String API_PATH = "/cars";
    private final CarService service;

    CarController(final CarService service) {
        this.service = service;
    }


    @GetMapping("/all")
     public Collection<Car> get() {
        return service.findAll();
    }


    @GetMapping("/{id}")
    public Car getById(@PathVariable final UUID id) {
        return service.findById(id);
    }


    @GetMapping
    public Collection<Car> get(@RequestParam(required = false) final String hersteller) {
        if (hersteller == null) {
            // Optional: Wenn der Parameter fehlt, kann hier eine leere Liste
            // oder eine Fehlermeldung zurückgegeben werden, anstatt findAll() aufzurufen.
            // Wir verwenden hier findAll(), aber es ist jetzt EINDEUTIG.
            return service.findAll();
        }
        return service.findByHersteller(hersteller);
    }
}
