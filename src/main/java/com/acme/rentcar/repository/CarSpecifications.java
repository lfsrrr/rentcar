package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

public final class CarSpecifications {

    private CarSpecifications() {
    }

    public static Specification<Car> withCriteria(final MultiValueMap<String, String> params) {
        final List<Specification<Car>> specs = new ArrayList<>();

        // Prüfen, ob params überhaupt existiert (NullAway safety)
        if (params == null || params.isEmpty()) {
            return (root, query, cb) -> null;
        }

        if (params.containsKey("hersteller")) {
            final String hersteller = params.getFirst("hersteller");
            if (hersteller != null && !hersteller.isBlank()) {
                specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("hersteller")), "%" + hersteller.toLowerCase() + "%"));
            }
        }

        if (params.containsKey("modell")) {
            final String modell = params.getFirst("modell");
            if (modell != null && !modell.isBlank()) {
                specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("modell")), "%" + modell.toLowerCase() + "%"));
            }
        }

        if (params.containsKey("minSitzplaetze")) {
            final String minSitzeStr = params.getFirst("minSitzplaetze");
            if (minSitzeStr != null && !minSitzeStr.isBlank()) {
                try {
                    final int minSitze = Integer.parseInt(minSitzeStr);
                    specs.add((root, query, cb) ->
                        cb.ge(root.get("details").get("sitzplaetze"), minSitze));
                } catch (NumberFormatException e) {
                    // Ignorieren
                }
            }
        }

        return specs.stream()
            .reduce(Specification::and)
            // FIX: Mehrdeutigkeit aufgelöst durch Lambda statt Specification.where(null)
            .orElse((root, query, cb) -> null);
    }
}
