package com.acme.rentcar.repository;

import com.acme.rentcar.controller.CarController;
import com.acme.rentcar.entity.Car;
import java.util.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * Client-Interface, das als Proxy für den CarController dient (Ersatz für TestRestTemplate).
 */
@HttpExchange
public interface CarClient {
    @GetExchange
    Collection<Car> get(@RequestParam MultiValueMap<String, String> suchparameter);

    @GetExchange("/{id}")
    ResponseEntity<Car> getById(@PathVariable String id);

    @GetExchange("/{id}")
    ResponseEntity<String> getByIdAsString(@PathVariable String id);
}
