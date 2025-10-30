package com.acme.rentcar.controller; // 1. Paketname angepasst (rentcar statt patientverwaltung)

import com.acme.rentcar.entity.Car; // 2. Entity von Patient zu Car geändert
import com.acme.rentcar.service.CarService; // 3. Service von PatientService zu CarService geändert
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CarController.API_PATH) // 4. Klassenname in RequestMapping angepasst
public class CarController { // 5. Klasse von PatientController zu CarController geändert

    // 6. API-Pfad von /patient zu /cars geändert (für Plural und REST-Konvention)
    static final String API_PATH = "/cars";

    // 7. Variablen von PatientService zu CarService geändert
    private final CarService service;

    public CarController(final CarService service) {
        this.service = service;
    }

    @GetMapping
        // 8. Rückgabetyp und Collection-Typ von Patient zu Car geändert
    Collection<Car> get() {
        return service.findAll();
    }
}
