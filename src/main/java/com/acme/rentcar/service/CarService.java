package com.acme.rentcar.service;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.repository.CarRepository;
import java.util.Collection;
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
}
