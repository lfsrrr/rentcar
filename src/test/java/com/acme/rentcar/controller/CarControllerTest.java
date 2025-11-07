package com.acme.rentcar.controller;

import com.acme.rentcar.Application;
import com.acme.rentcar.repository.CarClient;
import com.acme.rentcar.entity.Car;
import com.jayway.jsonpath.JsonPath;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.UriComponentsBuilder;
import static com.acme.rentcar.config.DevConfig.DEV;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail; // Wichtig: Import für fail()
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

// HINWEIS: Die SoftAssertionsExtension wurde entfernt, um Proxy-Konflikte zu vermeiden.

@Tag("integration")
@Tag("rest")
@Tag("rest-get")
@DisplayName("REST-Schnittstelle fuer GET-Requests testen")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(DEV)
@SuppressWarnings({
    "WriteTag",
    "ClassFanOutComplexity",
    "MissingJavadoc",
    "MissingJavadocType",
    "JavadocVariable",
    "PMD.AtLeastOneConstructor",
    "PMD.LinguisticNaming",
    "PMD.TooManyStaticImports"
})
final class CarControllerTest {
    // Angepasst an Ihre CarRepositoryFake
    private static final String ID_VORHANDEN = "c0714b62-9e9f-43b6-905c-d5f9d14620f1";
    private static final String ID_NICHT_VORHANDEN = "ffffffff-ffff-ffff-ffff-ffffffffffff";
    private static final String HERSTELLER_VORHANDEN = "BMW";
    private static final String UNKNOWN_HERSTELLER = "Audi";

    private static final String HERSTELLER_PARAM = "hersteller";
    private static final String API_PATH = "/cars";

    private static final String SCHEMA = "http";
    private static final String HOST = "localhost";

    private final CarClient carClient;

    @SuppressFBWarnings("CT")
    CarControllerTest(@LocalServerPort final int port, final ApplicationContext ctx) {
        assertThat(ctx).isNotNull();
        final var controller = ctx.getBean(CarController.class);
        assertThat(controller).isNotNull();

        final var uriComponents = UriComponentsBuilder.newInstance()
            .scheme(SCHEMA)
            .host(HOST)
            .port(port)
            .path(API_PATH)
            .build();
        final var baseUrl = uriComponents.toUriString();

        // Stark vereinfachte RestClient-Erstellung, um Proxy-Fehler zu vermeiden
        final var restClient = RestClient
            .builder()
            .baseUrl(baseUrl)
            .build();

        final var clientAdapter = RestClientAdapter.create(restClient);
        final var proxyFactory = HttpServiceProxyFactory.builderFor(clientAdapter).build();

        carClient = proxyFactory.createClient(CarClient.class);
    }

    @Test
    @DisplayName("Immer erfolgreich")
    void immerErfolgreich() {
        assertThat(true).isTrue(); // NOSONAR
    }

    // -----------------------------------------------------------------------------------
    // TEST: Lesen mit Query-Parameter
    // -----------------------------------------------------------------------------------

    @Test
    @DisplayName("Suche nach allen Autos")
    void findAll() {
        final MultiValueMap<String, String> suchparameter = MultiValueMap.fromSingleValue(Map.of());

        final var cars = carClient.get(suchparameter);

        assertThat(cars)
            .isNotNull()
            .isNotEmpty()
            .hasSize(2);
    }

    @ParameterizedTest(name = "[{index}] Suche mit vorhandenem Hersteller: hersteller={0}")
    @ValueSource(strings = HERSTELLER_VORHANDEN)
    @DisplayName("Suche mit vorhandenem Hersteller")
    void getByHersteller(final String hersteller) {
        final var suchparameter = MultiValueMap.fromSingleValue(Map.of(HERSTELLER_PARAM, hersteller));

        final var cars = carClient.get(suchparameter);

        assertThat(cars)
            .isNotNull()
            .isNotEmpty()
            .hasSize(1);
        cars.stream()
            .map(Car::getHersteller)
            .forEach(herstellerTmp -> assertThat(herstellerTmp).isEqualToIgnoringCase(hersteller));
    }

    @Test
    @DisplayName("Suche mit unbekanntem Hersteller (Kein Treffer)")
    void getByHerstellerNichtVorhanden() {
        final var suchparameter = MultiValueMap.fromSingleValue(Map.of(HERSTELLER_PARAM, UNKNOWN_HERSTELLER));

        final var cars = carClient.get(suchparameter);

        assertThat(cars)
            .isNotNull()
            .isEmpty();
    }

    // -----------------------------------------------------------------------------------
    // TEST: Lesen mit ID (Pfad-Parameter)
    // -----------------------------------------------------------------------------------

    @Nested
    @DisplayName("Suche anhand der ID")
    final class GetById {

        @ParameterizedTest(name = "[{index}] Suche mit vorhandener ID und JsonPath: id={0}")
        @ValueSource(strings = ID_VORHANDEN)
        @DisplayName("Suche mit vorhandener ID und JsonPath")
        void getByIdJson(final String id) {
            final var response = carClient.getByIdAsString(id);

            final var body = response.getBody();
            assertThat(body).isNotNull().isNotBlank();

            final var idPath = "$.id";
            final String idTmp = JsonPath.read(body, idPath);
            assertThat(idTmp).isEqualTo(id);

            final var herstellerPath = "$.hersteller";
            final String hersteller = JsonPath.read(body, herstellerPath);
            assertThat(hersteller).isNotNull();
        }

        @ParameterizedTest(name = "[{index}] Suche mit vorhandener ID: id={0}")
        @ValueSource(strings = ID_VORHANDEN)
        @DisplayName("Suche mit vorhandener ID")
        void getById(final String id) {
            final var response = carClient.getById(id);

            final var car = response.getBody();
            assertThat(car).isNotNull();
            assertThat(car.getId().toString()).isEqualTo(id);
            assertThat(car.getHersteller()).isNotNull();
        }

        @ParameterizedTest(name = "[{index}] Suche mit nicht-vorhandener ID: {0}")
        @ValueSource(strings = ID_NICHT_VORHANDEN)
        @DisplayName("Suche mit nicht-vorhandener ID")
        void getByIdNichtVorhanden(final String id) {
            // VERMEIDUNG des IllegalStateException-Fehlers durch try-catch
            try {
                carClient.getById(id);
                // Wenn der Aufruf nicht fehlschlägt, ist das ein Fehler
                fail("Es wurde keine 404 NOT FOUND Exception geworfen.");
            } catch (final HttpClientErrorException.NotFound exc) {
                // Bei Erfolg (404 geworfen)
                assertThat(exc.getStatusCode()).isEqualTo(NOT_FOUND);
            } catch (final Exception exc) {
                // Fängt alle unerwarteten Proxy-Fehler ab und lässt den Test als Fehler markieren
                fail("Unerwartete Exception: " + exc.getMessage());
            }
        }
    }
}
