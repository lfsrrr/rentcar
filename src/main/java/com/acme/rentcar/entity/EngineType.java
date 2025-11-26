package com.acme.rentcar.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;

/**
 * Zu CarDetails die Engine-Typ-Enumeration.
 *
 */


public enum EngineType {

    BENZIN("B"),

    DIESEL("D"),

    ELEKTRO("E"),

    HYBRID("H");

    private final String value;

    EngineType(final String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static EngineType of(final String value) {
        return Stream.of(values())
            .filter(engineType -> engineType.value.equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Ungueltiger EngineType: " + value));
    }
}
