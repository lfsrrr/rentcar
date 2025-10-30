package com.acme.rentcar.entity; // Muss im Entity-Paket liegen

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;


public enum EngineType {

    BENZIN("B"), // Benzin

    DIESEL("D"), // Diesel

    ELEKTRO("E"), // Elektro

    HYBRID("H"); // Hybrid

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
            .orElse(null);
    }
}
