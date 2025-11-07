package com.acme.rentcar.service;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.repository.CarRepository;
import java.util.Collection;
import java.util.UUID;
import org.springframework.stereotype.Service;


@Service
public class CarService {

    private final CarRepository repository;

    public CarService(final CarRepository repository) {
        this.repository = repository;
    }

    public Collection<Car> findAll() {
        return repository.findAll();
    }


    public Car findById(final UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Car not found with ID: " + id));
    }


    public Collection<Car> findByHersteller(final String hersteller) {
        return repository.findByHersteller(hersteller);
    }
}
