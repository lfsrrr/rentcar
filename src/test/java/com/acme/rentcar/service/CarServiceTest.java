package com.acme.rentcar.service;

import com.acme.rentcar.entity.Car;
import com.acme.rentcar.repository.CarRepository;
import com.acme.rentcar.repository.CarRepositoryFake;
import com.acme.rentcar.controller.CarWriteDTO;
import com.acme.rentcar.entity.EngineType;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.time.LocalDate;
import com.acme.rentcar.controller.CarWriteDTO;
import com.acme.rentcar.entity.EngineType;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("unit")
@Tag("service-read")
@ExtendWith(SoftAssertionsExtension.class)
@DisplayName("Geschaeftslogik fuer Lesen testen (Unit-Test)")
public final class CarServiceTest {

    // Testdaten (angepasst an Ihre CarRepositoryFake.java)
    private static final String ID_VORHANDEN = "c0714b62-9e9f-43b6-905c-d5f9d14620f1";
    private static final String HERSTELLER_VORHANDEN = "BMW";

    private final CarService service;

    @InjectSoftAssertions
    @SuppressWarnings("NullAway.Init")
    private SoftAssertions softly;

    // Konstruktor des Tests
    CarServiceTest() {
        // Manuelle Instanziierung des Fake-Repository mittels Reflection
        // (umgeht @Autowired und Spring Context)
        final var constructor = CarRepositoryFake.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        final CarRepository repo;
        try {
            repo = (CarRepository) constructor.newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Manuelle Instanziierung des Service mit dem Fake-Repository
        service = new CarService(repo);
    }

    @ParameterizedTest(name = "[{index}] Suche mit vorhandener ID: id={0}")
    @ValueSource(strings = ID_VORHANDEN)
    @DisplayName("Suche Auto mit id")
    public void findById(final String id) {
        //given
        final var carId = UUID.fromString(id);

        //when
        final var car = service.findById(carId);

        //then
        assertThat(car).isNotNull()
            .extracting(Car::getId)
            .isEqualTo(carId);
    }

    @ParameterizedTest(name = "[{index}] Suche mit vorhandenem Hersteller: hersteller={0}")
    @ValueSource(strings = HERSTELLER_VORHANDEN)
    @DisplayName("Suche Auto mit vorhandenem Hersteller")
    void find(final String hersteller) {
        //given
        // (keine Map notwendig, da die Service-Methode einen String erwartet)

        //when
        final var cars = service.findByHersteller(hersteller);

        //then
        softly.assertThat(cars)
            .isNotNull();
        cars.stream()
            .map(Car::getHersteller)
            .forEach(herstellerTmp -> softly.assertThat(herstellerTmp)
                .isEqualTo(HERSTELLER_VORHANDEN));
    }

    @Test
    @DisplayName("Neues Auto anlegen (Create)")
    void createCar() {
        // given
        // 1. Erstellen Sie ein gültiges DTO (Data Transfer Object)
        final var dto = new CarWriteDTO(
            "Mercedes",
            "C-Klasse",
            "KA-MB-123", // Gültiges Kennzeichen-Pattern
            LocalDate.of(2024, 1, 1),
            EngineType.HYBRID,
            5, // Positive sitzplaetze
            "Silber"
        );

        // when
        // 2. Rufen Sie die create-Methode im Service auf
        final var result = service.create(dto);

        // then
        // 3. Prüfen Sie das Ergebnis
        assertThat(result).isNotNull();
        // Prüfen, ob die Daten aus dem DTO korrekt übernommen wurden
        assertThat(result.getHersteller()).isEqualTo("Mercedes");
        assertThat(result.getDetails().getFarbe()).isEqualTo("Silber");
    }

}
