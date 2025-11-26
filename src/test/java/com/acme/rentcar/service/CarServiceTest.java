package com.acme.rentcar.service;

import com.acme.rentcar.controller.CarWriteDTO;
import com.acme.rentcar.entity.Car;
import com.acme.rentcar.entity.EngineType;
import com.acme.rentcar.repository.CarRepository;
import java.time.LocalDate;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@Tag("service-read")
@ExtendWith(SoftAssertionsExtension.class)
@DisplayName("Geschaeftslogik fuer Lesen testen (Unit-Test)")
public final class CarServiceTest {

    private static final String ID_VORHANDEN = "c0714b62-9e9f-43b6-905c-d5f9d14620f1";
    private static final String HERSTELLER_VORHANDEN = "BMW";
    private static final String UNKNOWN_HERSTELLER = "Opel";

    private final CarService service;

    @InjectSoftAssertions
    @SuppressWarnings("NullAway.Init")
    private SoftAssertions softly;


    CarServiceTest() {
        final CarRepository repo;
        try {

            final Class<?> fakeClass = Class.forName("com.acme.rentcar.repository.CarRepositoryFake");

            final var constructor = fakeClass.getDeclaredConstructor();
            constructor.setAccessible(true);

            repo = (CarRepository) constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Konnte Fake-Repository nicht laden: " + e.getMessage(), e);
        }

        service = new CarService(repo);
    }

    @ParameterizedTest(name = "[{index}] Suche mit vorhandener ID: id={0}")
    @ValueSource(strings = ID_VORHANDEN)
    @DisplayName("Suche Auto mit id")
    void findById(final String id) {

        final var carId = UUID.fromString(id);


        final var car = service.findById(carId);


        assertThat(car).isNotNull()
            .extracting(Car::getId)
            .isEqualTo(carId);
    }

    @ParameterizedTest(name = "[{index}] Suche mit vorhandenem Hersteller: hersteller={0}")
    @ValueSource(strings = HERSTELLER_VORHANDEN)
    @DisplayName("Suche Auto mit vorhandenem Hersteller")
    void findByHersteller(final String hersteller) {



        final var cars = service.findByHersteller(hersteller);


        softly.assertThat(cars)
            .isNotNull()
            .isNotEmpty();

        cars.stream()
            .map(Car::getHersteller)
            .forEach(herstellerTmp -> softly.assertThat(herstellerTmp)
                .isEqualTo(HERSTELLER_VORHANDEN));
    }

    @Test
    @DisplayName("Suche Auto mit unbekanntem Hersteller (Keine Treffer)")
    void findByHerstellerNichtVorhanden() {
        //given

        //when
        final var cars = service.findByHersteller(UNKNOWN_HERSTELLER);

        //then
        assertThat(cars).isNotNull().isEmpty();
    }

    // Test für Neuanlegen (Create)
    @Test
    @DisplayName("Neues Auto anlegen (Create)")
    void createCar() {
        // given
        final var dto = new CarWriteDTO(
            "Mercedes",
            "C-Klasse",
            "KA-MB-123",
            LocalDate.of(2024, 1, 1),
            EngineType.HYBRID,
            5,
            "Silber"
        );

        // when
        final var result = service.create(dto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getHersteller()).isEqualTo("Mercedes");
        assertThat(result.getDetails().getFarbe()).isEqualTo("Silber");
    }
}
