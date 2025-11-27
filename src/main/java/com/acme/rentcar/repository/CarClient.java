package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import java.util.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/// Client-Interface für die Kommunikation mit der Car-API.
///
/// Definiert die HTTP-Aufrufe gegen den `/cars` Endpunkt unter Verwendung
/// von Spring 6 HTTP Interfaces.
@HttpExchange("/cars")
public interface CarClient {

    /// Ruft eine Kollektion von Autos ab, gefiltert durch Suchparameter.
    ///
    /// @param suchparameter Eine Map mit Query-Parametern (z.B. ?color=red), die an die URL angehängt werden.
    /// @return Eine Sammlung von `Car`-Objekten, die den Kriterien entsprechen.
    @GetExchange
    Collection<Car> get(@RequestParam MultiValueMap<String, String> suchparameter);

    /// Lädt ein spezifisches Auto anhand der ID.
    ///
    /// @param id Die eindeutige Kennung des Autos, das abgerufen werden soll.
    /// @return Ein `ResponseEntity`, das das gefundene `Car`-Objekt enthält.
    @GetExchange("/{id}")
    ResponseEntity<Car> getById(@PathVariable String id);

    /// Lädt ein spezifisches Auto anhand der ID, gibt jedoch den rohen Body als String zurück.
    ///
    /// Nützlich für Debugging oder wenn das Mapping manuell erfolgen soll.
    ///
    /// @param id Die eindeutige Kennung des Autos.
    /// @return Ein `ResponseEntity`, das die Antwort als rohen String enthält.
    @GetExchange("/{id}")
    ResponseEntity<String> getByIdAsString(@PathVariable String id);
}
