package com.acme.rentcar.service;

import com.acme.rentcar.controller.CarDTO;
import com.acme.rentcar.entity.Car;
import com.acme.rentcar.entity.EngineType;
import com.acme.rentcar.repository.CarRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/// Implementiert den Unit-Test für CarService mit Mockito.
@Tag("unit")
@Tag("service")
@ExtendWith(MockitoExtension.class)
@DisplayName("Geschäftslogik testen (Unit-Test)")
class CarServiceTest {

    @Mock
    @SuppressWarnings("NullAway.Init") // Mockito initialisiert dieses Feld via Reflection
    private CarRepository repository;

    @InjectMocks
    @SuppressWarnings("NullAway.Init") // Mockito initialisiert dieses Feld via Reflection
    private CarService service;

    @Test
    @DisplayName("Suche Auto mit ID (Mock)")
    void findById() {
        // given
        final var id = UUID.randomUUID();
        final var car = new Car();
        car.setId(id);

        // WICHTIG: Wir mocken hier 'findCarById', da dies die Methode ist,
        // die der Service intern aufruft (statt der Standard 'findById').
        when(repository.findCarById(id)).thenReturn(car);

        // when
        final var result = service.findById(id);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        verify(repository).findCarById(id);
    }

    @Test
    @DisplayName("Suche Auto nach Hersteller (Mock)")
    void findByHersteller() {
        // given
        final var hersteller = "BMW";
        final var car = new Car();
        car.setHersteller(hersteller);

        // WICHTIG: Wir mocken 'findByHerstellerIgnoreCase'
        when(repository.findByHerstellerIgnoreCase(hersteller)).thenReturn(List.of(car));

        // when
        final var result = service.findByHersteller(hersteller);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.iterator().next().getHersteller()).isEqualTo(hersteller);
        verify(repository).findByHerstellerIgnoreCase(hersteller);
    }

    @Test
    @DisplayName("Neues Auto anlegen (Create)")
    void createCar() {
        // given
        final var dto = new CarDTO(
            "Mercedes",
            "C-Klasse",
            "KA-MB-123",
            LocalDate.of(2024, 1, 1),
            EngineType.HYBRID,
            5,
            "Silber"
        );

        // Wir simulieren das Speichern: Das Repo gibt das Auto zurück, das rein kam.
        // Wir simulieren, dass die DB eine ID generiert hat.
        when(repository.save(any(Car.class))).thenAnswer(invocation -> {
            final var car = (Car) invocation.getArgument(0);
            if (car.getId() == null) {
                car.setId(UUID.randomUUID());
            }
            if (car.getDetails().getId() == null) {
                car.getDetails().setId(UUID.randomUUID());
            }
            return car;
        });

        // when
        final var result = service.create(dto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getHersteller()).isEqualTo("Mercedes");
        assertThat(result.getDetails().getFarbe()).isEqualTo("Silber");
        verify(repository).save(any(Car.class));
    }
}
