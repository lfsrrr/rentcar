/*
 * Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.acme.kunde.controller;

import com.acme.kunde.entity.Kunde;
import com.acme.kunde.service.KundeService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.acme.kunde.controller.Constants.API_PATH;
import static com.acme.kunde.controller.Constants.ID_PATTERN;
import static com.acme.kunde.controller.Constants.VERSION_1;
import static com.acme.kunde.controller.Constants.VERSION_1_EXAMPLE;
import static com.acme.kunde.controller.Constants.X_VERSION;

/// Eine Controller-Klasse bildet die REST-Schnittstelle, wobei die HTTP-Methoden, Pfade und MIME-Typen auf die
/// Methoden der Klasse abgebildet werden.
/// ![Klassendiagramm](../../../../../asciidoc/KundeController.svg)
///
/// @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)
// Maven: ![Klassendiagramm](../../../../../../generated-docs/KundeController.svg)
@RestController
@RequestMapping(API_PATH)
@OpenAPIDefinition(info = @Info(title = "Kunde API", version = VERSION_1))
@SuppressWarnings("java:S1075")
class KundeController {
    /// Pfad, um Nachnamen abzufragen.
    private static final String NACHNAME_PATH = "/nachname";
    private static final String SUCHEN_TAG = "Suchen";

    /// Pfad, um Nachnamen abzufragen.
    private final KundeService service;
    private final StableValue<Logger> logger = StableValue.of();

    /// Konstruktor mit _package private_ für _Spring_.
    ///
    /// @param service Injiziertes Service-Objekt.
    KundeController(final KundeService service) {
        this.service = service;
    }

    // https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-ann-methods
    // https://localhost:8443/swagger-ui.html
    /// Suche anhand der Kunde-ID als Pfad-Parameter.
    ///
    /// @param id ID des zu suchenden Kunden
    /// @return Gefundener Kunde.
    @GetMapping(path = "{id:" + ID_PATTERN + "}", version = VERSION_1)
    @Operation(summary = "Suche mit der Kunde-ID", tags = SUCHEN_TAG)
    @Parameter(name = X_VERSION, in = ParameterIn.HEADER, example = VERSION_1_EXAMPLE)
    @ApiResponse(responseCode = "200", description = "Kunde gefunden")
    @ApiResponse(responseCode = "404", description = "Kunde nicht gefunden")
    Kunde getById(@PathVariable final UUID id) {
        getLogger().debug("getById: id={}, Thread={}", id, Thread.currentThread().getName());

        // Geschaeftslogik
        final var kunde = service.findById(id);

        getLogger().debug("getById: kunde={}", kunde);
        return kunde;
    }

    /// Suche mit diversen Query-Parameter.
    ///
    /// @param queryparam Query-Parameter als Map .
    /// @return Gefundenen Kunden als [Collection].
    @GetMapping(version = VERSION_1)
    @Operation(summary = "Suche mit Query-Parameter", tags = SUCHEN_TAG)
    @Parameter(name = X_VERSION, in = ParameterIn.HEADER, example = VERSION_1_EXAMPLE)
    @ApiResponse(responseCode = "200", description = "Collection mit den Kunden")
    @ApiResponse(responseCode = "404", description = "Keine Kunden gefunden")
    Collection<Kunde> get(@RequestParam final MultiValueMap<String, String> queryparam) {
        getLogger().debug("get: queryparam={}", queryparam);

        // Geschaeftslogik
        final var kunden = service.find(queryparam);

        getLogger().debug("get: kunden={}", kunden);
        return kunden;
    }

    /// Beispiel für Deprecation.
    ///
    /// @return JSON-Datensatz als Platzhalter.
    @GetMapping(path = "deprecated", version = "0.0.1")
    @Parameter(name = X_VERSION, in = ParameterIn.HEADER, example = "0.0.1")
    @Operation(summary = "Beispiel fuer Deprecation", tags = SUCHEN_TAG)
    @ApiResponse(responseCode = "200", description = "JSON-Datensatz als Platzhalter")
    Map<String, String> deprecated() {
        getLogger().debug("deprecated");
        return Map.of("deprecated", "Support ist abgelaufen");
    }

    /// Abfrage, welche Nachnamen es zu einem Präfix gibt.
    ///
    /// @param prefix Nachname-Präfix als Pfadvariable.
    /// @return Die passenden Nachnamen oder Statuscode `404`, falls es keine gibt.
    @GetMapping(path = NACHNAME_PATH + "/{prefix}", version = VERSION_1)
    @Operation(summary = "Suche Nachnamen mit Praefix", tags = SUCHEN_TAG)
    @Parameter(name = X_VERSION, in = ParameterIn.HEADER, example = VERSION_1_EXAMPLE)
    Map<String, Collection<String>> getNachnamenByPrefix(@PathVariable final String prefix) {
        getLogger().debug("getNachnamenByPrefix: {}", prefix);
        final var nachnamen = service.findNachnamenByPrefix(prefix);
        getLogger().debug("getNachnamenByPrefix: {}", nachnamen);
        return Map.of("nachnamen", nachnamen);
    }

    private Logger getLogger() {
        return logger.orElseSet(() -> LoggerFactory.getLogger(KundeController.class));
    }
}
