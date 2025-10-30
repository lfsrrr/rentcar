package com.acme.rentcar.service;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.repository.CarRepository; // Wird gleich erstellt
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service // Markiert diese Klasse als Spring Service Komponente
public class CarService {

    // Finales Feld für das Repository (Dependency Injection)
    private final CarRepository repository;

    // Konstruktor-Injection (empfohlen in Spring)
    public CarService(final CarRepository repository) {
        this.repository = repository;
    }

    /**
     * Sucht alle Car-Entitäten.
     * @return Eine Collection aller Autos.
     */
    public Collection<Car> findAll() {
        // Ruft die Leseoperation vom Repository ab.
        return repository.findAll();
    }

    // Schreibenoperationen (z.B. save, delete) werden für diesen Meilenstein NICHT implementiert.
}
