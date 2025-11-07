package com.acme.rentcar.repository;

import com.acme.rentcar.controller.CarController;
import com.acme.rentcar.entity.Car;
import java.util.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * Client-Interface, das als Proxy für den CarController dient (Ersatz für TestRestTemplate).
 */
@HttpExchange
public interface CarClient {

    // Entspricht GET /cars oder GET /cars?hersteller=X
    // Der Pfad wird über CarController.API_PATH referenziert (was jetzt public ist).
    @GetMapping(CarController.API_PATH)
    Collection<Car> get(@RequestParam MultiValueMap<String, String> suchparameter);

    // Entspricht GET /cars/{id} für die Erfolgsfälle
    @GetMapping(CarController.API_PATH + "/{id}")
    ResponseEntity<Car> getById(@PathVariable String id);

    // Entspricht GET /cars/{id} für JsonPath-Tests (gibt Body als String zurück)
    @GetMapping(CarController.API_PATH + "/{id}")
    ResponseEntity<String> getByIdAsString(@PathVariable String id);
}
