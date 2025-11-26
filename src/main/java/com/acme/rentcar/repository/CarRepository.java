package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CarRepository {

    Collection<Car> findAll();

    Optional<Car> findById(UUID id);

    Collection<Car> findByHersteller(String hersteller);

    Car save(Car car);
    Optional<Car> update(Car car);

}
