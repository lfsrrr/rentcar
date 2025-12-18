package com.acme.rentcar.controller;

import com.acme.rentcar.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/// REST-Controller für die Verwaltung von Autos.
///
/// Dieser Controller stellt die API-Endpunkte bereit, um Fahrzeuge zu suchen,
/// Details abzurufen, neue Fahrzeuge anzulegen und bestehende Daten zu aktualisieren.
/// Er fungiert als Schnittstelle zwischen dem HTTP-Client (z.B. Browser, Bruno) und
/// der Geschäftslogik im [CarService].
///
/// Diagramm zur Struktur:
/// ![Klassendiagramm](../../../../../../generated-docs/CarController.svg)
@RestController
@RequestMapping("/cars")
@SuppressWarnings("preview")
final class CarController {

    private static final StableValue<Logger> LOGGER = StableValue.of();

    static {
        LOGGER.setOrThrow(LoggerFactory.getLogger(CarController.class));
    }

    private final CarService service;

    /// Erstellt den Controller und injiziert den benötigten Service.
    CarController(final CarService service) {
        this.service = service;
    }

    /// Sucht nach Autos basierend auf flexiblen Filterkriterien.
    ///
    /// Clients können Parameter wie `?hersteller=VW` oder `?minSitzplaetze=5` übergeben.
    /// Werden keine Parameter angegeben, liefert die Methode eine Liste aller vorhandenen Autos.
    ///
    /// @param searchParams Eine Map aller Query-Parameter aus der URL.
    /// @return Eine Liste von [CarDTO] Objekten, die den Kriterien entsprechen.
    @Operation(summary = "Autos suchen / filtern")
    @GetMapping
    Collection<CarDTO> get(@RequestParam(required = false) final MultiValueMap<String, String> searchParams) {
        LOGGER.orElseThrow().debug("GET /cars mit params: {}", searchParams);
        return service.find(searchParams);
    }

    /// Ruft die Details eines spezifischen Autos anhand seiner ID ab.
    ///
    /// @param id Die UUID des gesuchten Autos.
    /// @return Das gefundene Auto als [CarDTO] mit Status 200 OK.
    ///         Wirft eine Exception (404), wenn das Auto nicht existiert.
    @Operation(summary = "Auto per ID suchen")
    @ApiResponse(responseCode = "200", description = "Auto gefunden")
    @ApiResponse(responseCode = "404", description = "Auto nicht gefunden")
    @GetMapping("/{id}")
    ResponseEntity<CarDTO> getById(@PathVariable final UUID id) {
        final var carDto = service.findById(id);
        return ResponseEntity.ok(carDto);
    }

    /// Legt ein neues Auto in der Datenbank an.
    ///
    /// Die übergebenen Daten werden vor der Verarbeitung validiert (z.B. Pflichtfelder, Formate).
    /// Nach erfolgreicher Erstellung wird der Status 201 Created zurückgegeben, zusammen mit
    /// dem `Location`-Header, der auf die URL des neuen Autos verweist.
    ///
    /// @param carDto Die Daten des neuen Autos (ohne ID).
    /// @return Eine leere Antwort mit Status 201 und Location-Header.
    @Operation(summary = "Neues Auto anlegen")
    @ApiResponse(responseCode = "201", description = "Erfolgreich angelegt")
    @PostMapping
    ResponseEntity<Void> createCar(@RequestBody @Valid final CarDTO carDto) {
        final var createdCar = service.create(carDto);
        final var location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            // Zugriff auf ID im Record ist id(), nicht getId()
            .buildAndExpand(createdCar.id())
            .toUri();
        return ResponseEntity.created(location).build();
    }

    /// Aktualisiert die Daten eines bestehenden Autos.
    ///
    /// Überschreibt die Eigenschaften des Autos mit der ID `id` mit den Werten aus `carDto`.
    ///
    /// @param id     Die UUID des zu ändernden Autos.
    /// @param carDto Die neuen Daten für das Auto.
    /// @return Das aktualisierte Auto als [CarDTO] mit Status 200 OK.
    @Operation(summary = "Auto aktualisieren")
    @PutMapping("/{id}")
    ResponseEntity<CarDTO> updateCar(
        @PathVariable final UUID id,
        @RequestBody @Valid final CarDTO carDto
    ) {
        final var updatedCar = service.update(id, carDto);
        return ResponseEntity.ok(updatedCar);
    }
}
