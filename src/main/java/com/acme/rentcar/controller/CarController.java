package com.acme.rentcar.controller;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.service.CarService;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CarController.API_PATH)
public class CarController {


    static final String API_PATH = "/cars";


    private final CarService service;

    public CarController(final CarService service) {
        this.service = service;
    }

    @GetMapping

    Collection<Car> get() {
        return service.findAll();
    }
}
