package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/// Hilfsklasse zum Bauen von dynamischen JPA-Queries (Specifications).
public final class CarSpecifications {

    private CarSpecifications() {
        // Utility class
    }

    /// Erstellt eine Specification basierend auf Query-Parametern.
    ///
    /// Unterstützt Filterung nach:
    /// - hersteller (Teilstring, case-insensitive)
    /// - modell (Teilstring, case-insensitive)
    /// - minSitzplaetze (Größer oder gleich)
    ///
    /// @param params Die Query-Parameter aus der URL.
    /// @return Die kombinierte Specification.
    public static Specification<Car> withCriteria(final MultiValueMap<String, String> params) {
        final List<Specification<Car>> specs = new ArrayList<>();

        // 1. Filter: Hersteller
        if (params.containsKey("hersteller")) {
            final String hersteller = params.getFirst("hersteller");
            if (hersteller != null && !hersteller.isBlank()) {
                specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("hersteller")), "%" + hersteller.toLowerCase() + "%"));
            }
        }

        // 2. Filter: Modell
        if (params.containsKey("modell")) {
            final String modell = params.getFirst("modell");
            if (modell != null && !modell.isBlank()) {
                specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("modell")), "%" + modell.toLowerCase() + "%"));
            }
        }

        // 3. Filter: Mindestanzahl Sitzplätze (greift auf die Relation 'details' zu)
        if (params.containsKey("minSitzplaetze")) {
            final String minSitzeStr = params.getFirst("minSitzplaetze");
            if (minSitzeStr != null && !minSitzeStr.isBlank()) {
                try {
                    final int minSitze = Integer.parseInt(minSitzeStr);
                    specs.add((root, query, cb) ->
                        cb.ge(root.get("details").get("sitzplaetze"), minSitze));
                } catch (NumberFormatException e) {
                    // Ignorieren ungültiger Zahlen
                }
            }
        }

        // Alle Specifications mit AND verknüpfen
        return specs.stream()
            .reduce(Specification::and)
            .orElse(null); // null bedeutet: kein Filter (alles laden)
    }
}
