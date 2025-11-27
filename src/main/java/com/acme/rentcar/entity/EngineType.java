package com.acme.rentcar.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;

///
/// Zu CarDetails die Engine-Typ-Enumeration.
///
public enum EngineType {

    /// Benzinmotor.
    BENZIN("B"),

    /// Dieselmotor.
    DIESEL("D"),

    /// Elektromotor.
    ELEKTRO("E"),

    /// Hybridantrieb.
    HYBRID("H");

    private final String value;

    EngineType(final String value) {
        this.value = value;
    }

    /// Gibt den internen Wert zurück.
    ///
    /// @return Der Wert als String.
    @JsonValue
    public String getValue() {
        return value;
    }

    /// Erstellt den Enum-Wert aus einem String.
    ///
    /// @param value Der String-Wert (z.B. "B").
    /// @return Der passende EngineType.
    @JsonCreator
    public static EngineType of(final String value) {
        return Stream.of(values())
            .filter(engineType -> engineType.value.equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Ungueltiger EngineType: " + value));
    }
}
