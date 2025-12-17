package com.acme.rentcar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.acme.rentcar.controller.CarDTO;
import com.acme.rentcar.controller.CarMapper;
import com.acme.rentcar.entity.Car;
import com.acme.rentcar.entity.CarDetails;
import com.acme.rentcar.entity.EngineType;
import com.acme.rentcar.repository.CarRepository;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

/// Implementiert den Unit-Test für CarService mit Mockito.
@Tag("unit")
@Tag("service")
@ExtendWith(MockitoExtension.class)
@DisplayName("Geschäftslogik für CarService testen (Unit-Test)")
@SuppressWarnings("NullAway") // Mockito initialisiert die Felder per Reflection
class CarServiceTest {

    private static final UUID ID_VORHANDEN = UUID.fromString("c0714b62-9e9f-43b6-905c-d5f9d14620f1");
    private static final String HERSTELLER_BMW = "BMW";

    @Mock
    private CarRepository repository;

    @Mock
    private CarMapper mapper;

    @InjectMocks
    private CarService service;

    /// Testet die findById-Funktion
    @Test
    @DisplayName("Suche Auto mit vorhandener ID")
    void findById() {
        // GIVEN
        final var carEntity = createCarEntity(ID_VORHANDEN, HERSTELLER_BMW);
        final var expectedDto = createCarDTO(ID_VORHANDEN, HERSTELLER_BMW);

        // Mock-Verhalten definieren
        when(repository.findCarById(ID_VORHANDEN)).thenReturn(carEntity);
        when(mapper.toDTO(carEntity)).thenReturn(expectedDto);

        // WHEN
        final var result = service.findById(ID_VORHANDEN);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(ID_VORHANDEN);
        assertThat(result.hersteller()).isEqualTo(HERSTELLER_BMW);

        verify(repository).findCarById(ID_VORHANDEN);
    }

    /// Testet den Fehlerfall bei nicht vorhandener ID
    @Test
    @DisplayName("Suche Auto mit nicht vorhandener ID wirft Exception")
    void findByIdNotFound() {
        // GIVEN
        final var unknownId = UUID.randomUUID();
        when(repository.findCarById(unknownId)).thenReturn(null);

        // WHEN / THEN
        assertThatThrownBy(() -> service.findById(unknownId))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Car not found");
    }

    /// Erfolgreiches Suchen mit Filter-Parametern
    @Test
    @DisplayName("Suche Auto mit Filter (Hersteller)")
    void findWithFilter() {
        // GIVEN
        final var params = new LinkedMultiValueMap<String, String>();
        params.add("hersteller", HERSTELLER_BMW);

        final var carEntity = createCarEntity(ID_VORHANDEN, HERSTELLER_BMW);
        final var carDto = createCarDTO(ID_VORHANDEN, HERSTELLER_BMW);

        // Mocking: Generische any() Aufrufe verhindern Warnungen
        when(repository.findAll(any(Specification.class), any(Sort.class)))
            .thenReturn(List.of(carEntity));
        when(mapper.toDTOs(any())).thenReturn(List.of(carDto));

        // WHEN
        final var results = service.find(params);

        // THEN
        assertThat(results).isNotEmpty();
        assertThat(results).extracting(CarDTO::hersteller)
            .contains(HERSTELLER_BMW);
    }

    /// Testet, dass bei unbekannten Kriterien eine leere Liste zurückkommt
    @Test
    @DisplayName("Suche Auto mit unbekanntem Filter (Keine Treffer)")
    void findWithUnknownFilter() {
        // GIVEN
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("hersteller", "Unbekannt");

        when(repository.findAll(any(Specification.class), any(Sort.class)))
            .thenReturn(Collections.emptyList());
        when(mapper.toDTOs(Collections.emptyList())).thenReturn(Collections.emptyList());

        // WHEN
        final var results = service.find(params);

        // THEN
        assertThat(results).isNotNull().isEmpty();
    }

    /// Test für Neuanlegen (Create)
    @Test
    @DisplayName("Neues Auto anlegen (Create)")
    void createCar() {
        // GIVEN
        final var inputDto = new CarDTO(
            "Mercedes",
            "C-Klasse",
            "KA-MB-123",
            LocalDate.of(2024, 1, 1),
            EngineType.HYBRID,
            5,
            "Silber"
        );

        // Das Entity, das "gespeichert" wird (mit generierter ID)
        final var savedEntity = new Car(UUID.randomUUID(), inputDto.hersteller(), inputDto.modell(),
            inputDto.erstzulassung(), inputDto.kennzeichen(), null, List.of());
        final var details = new CarDetails(UUID.randomUUID(), inputDto.farbe(), inputDto.sitzplaetze(),
            inputDto.motor(), Year.of(2024));
        savedEntity.setDetails(details);

        // Das erwartete Rückgabe-DTO
        final var expectedResultDto = new CarDTO(savedEntity.getId(), inputDto.hersteller(), inputDto.modell(),
            inputDto.kennzeichen(), inputDto.erstzulassung(), inputDto.motor(), inputDto.sitzplaetze(), inputDto.farbe());

        // Mocks
        when(repository.save(any(Car.class))).thenReturn(savedEntity);
        when(mapper.toDTO(savedEntity)).thenReturn(expectedResultDto);

        // WHEN
        final var result = service.create(inputDto);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.hersteller()).isEqualTo("Mercedes");
        assertThat(result.farbe()).isEqualTo("Silber"); // Checkt Details indirekt über DTO
        assertThat(result.id()).isNotNull();

        verify(repository).save(any(Car.class));
    }

    // --- Hilfsmethoden für Testdaten ---

    private Car createCarEntity(final UUID id, final String hersteller) {
        final var car = new Car();
        car.setId(id);
        car.setHersteller(hersteller);
        car.setModell("TestModell");
        car.setKennzeichen("KA-TEST-1");
        // FIX: Explizite Zeitzone für LocalDate.now()
        car.setErstzulassung(LocalDate.now(ZoneId.systemDefault()));
        return car;
    }

    private CarDTO createCarDTO(final UUID id, final String hersteller) {
        // FIX: Explizite Zeitzone für LocalDate.now()
        return new CarDTO(id, hersteller, "TestModell", "KA-TEST-1",
            LocalDate.now(ZoneId.systemDefault()), EngineType.BENZIN, 4, "Schwarz");
    }
}
