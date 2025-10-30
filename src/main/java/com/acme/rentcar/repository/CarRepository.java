package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import java.util.Collection;


public interface CarRepository {

    Collection<Car> findAll();

}
