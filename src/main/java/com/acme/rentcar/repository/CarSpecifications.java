package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

/// Utility-Klasse zum Erstellen von JPA-Specifications.
public final class CarSpecifications {

    private CarSpecifications() {
        // Utility class
    }

    /// Erstellt eine dynamische Specification basierend auf Query-Parametern.
    public static Specification<Car> withCriteria(final MultiValueMap<String, String> params) {
        if (params == null || params.isEmpty()) {
            return (root, query, cb) -> null;
        }

        final List<Specification<Car>> specs = new ArrayList<>();

        // Logik ausgelagert, um Komplexität zu senken (Cyclomatic & NPath)
        addStringFilter(specs, params, "hersteller");
        addStringFilter(specs, params, "modell");
        addSitzplaetzeFilter(specs, params);

        return specs.stream()
            .reduce(Specification::and)
            .orElse((root, query, cb) -> null);
    }

    /// Hilfsmethode für Text-Filter (Case-Insensitive LIKE).
    private static void addStringFilter(final List<Specification<Car>> specs,
                                        final MultiValueMap<String, String> params,
                                        final String key) {
        final String value = params.getFirst(key);
        if (value != null && !value.isBlank()) {
            specs.add((root, query, cb) ->
                cb.like(cb.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
        }
    }

    /// Hilfsmethode für Sitzplätze (Integer Parsing).
    private static void addSitzplaetzeFilter(final List<Specification<Car>> specs,
                                             final MultiValueMap<String, String> params) {
        final String minSitzeStr = params.getFirst("minSitzplaetze");
        if (minSitzeStr != null && !minSitzeStr.isBlank()) {
            try {
                final int minSitze = Integer.parseInt(minSitzeStr);
                specs.add((root, query, cb) ->
                    cb.ge(root.get("details").get("sitzplaetze"), minSitze));
            } catch (NumberFormatException e) {
                // Ungültige Zahl ignorieren
            }
        }
    }
}
