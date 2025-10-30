/*
 * Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.acme.kunde.service;

import com.acme.kunde.repository.AdresseBuilder;
import com.acme.kunde.repository.KundeBuilder;
import com.acme.kunde.repository.KundeRepository;
import com.acme.kunde.repository.RechnungBuilder;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.gaul.modernizer_maven_annotations.SuppressModernizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static com.acme.kunde.entity.FamilienstandType.LEDIG;
import static com.acme.kunde.entity.GeschlechtType.WEIBLICH;
import static com.acme.kunde.entity.InteresseType.LESEN;
import static com.acme.kunde.entity.InteresseType.REISEN;
import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.condition.JRE.JAVA_25;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Tag("unit")
@Tag("service-write")
@DisplayName("package-info.java fuer Schreiben testen")
@Execution(CONCURRENT)
@EnabledForJreRange(min = JAVA_25, max = JAVA_25)
@ExtendWith(SoftAssertionsExtension.class)
@SuppressWarnings("WriteTag")
class KundeWriteServiceTest {
    private static final String NEUE_PLZ = "12345";
    private static final String NEUER_ORT = "Testort";
    private static final String NEUER_NACHNAME = "Neuernachname";
    private static final String NEUE_EMAIL = "email@test.de";
    private static final String NEUES_GEBURTSDATUM = "2024-02-01";
    private static final String CURRENCY_CODE = "EUR";
    private static final String NEUE_HOMEPAGE = "https://test.de";

    private static final String ID_UPDATE = "00000000-0000-0000-0000-000000000030";
    private static final String ID_DELETE = "00000000-0000-0000-0000-000000000050";

    private final KundeWriteService service;
    private final KundeRepository repo;

    @InjectSoftAssertions
    @SuppressWarnings("NullAway.Init")
    private SoftAssertions softly;

    @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
    @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
    KundeWriteServiceTest() {
        final var constructor = KundeRepository.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        try {
            repo = (KundeRepository) constructor.newInstance();
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException(ex);
        }

        service = new KundeWriteService(repo);
    }

    @ParameterizedTest(name = "[{index}] Neuanlegen eines neuen Kunden: nachname={0}, email={1}")
    @CsvSource(
        NEUER_NACHNAME + "," + NEUE_EMAIL + "," + NEUES_GEBURTSDATUM + "," + CURRENCY_CODE + "," + NEUE_HOMEPAGE +
            "," + NEUE_PLZ + "," + NEUER_ORT
    )
    @DisplayName("Neuanlegen eines neuen Kunden")
    @SuppressWarnings({"MagicNumber", "BooleanExpressionComplexity"})
    void create(final ArgumentsAccessor args) {
        // given
        final var nachname = args.getString(0);
        final var email = args.getString(1);
        final var geburtsdatum = args.get(2, LocalDate.class);
        final var waehrung = args.getString(3);
        final var homepage = args.get(4, URL.class);
        final var plz = args.getString(5);
        final var ort = args.getString(6);
        if (nachname == null || email == null || geburtsdatum == null || waehrung == null || homepage == null ||
            plz == null || ort == null) {
            throw new IllegalStateException("Argumente duerfen nicht null sein");
        }
        final var adresse = AdresseBuilder
            .getBuilder()
            .setPlz(plz)
            .setOrt(ort)
            .build();
        final var rechnungen = List.of(
            RechnungBuilder
                .getBuilder()
                .setBetrag(ONE)
                .setWaehrung(Currency.getInstance(waehrung))
                .build()
        );
        final var kunde = KundeBuilder
            .getBuilder()
            .setNachname(nachname)
            .setEmail(email)
            .setHasNewsletter(true)
            .setGeburtsdatum(geburtsdatum)
            .setHomepage(homepage)
            .setGeschlecht(WEIBLICH)
            .setFamilienstand(LEDIG)
            .setAdresse(adresse)
            .setRechnungen(rechnungen)
            .setInteressen(List.of(LESEN, REISEN))
            .build();

        // when
        final var kundeCreated = service.create(kunde);

        // then
        softly.assertThat(kundeCreated.getId()).isNotNull();
        softly.assertThat(kundeCreated.getEmail()).isEqualTo(NEUE_EMAIL);
        softly.assertThat(kundeCreated.getAdresse().getPlz()).isEqualTo(NEUE_PLZ);
    }

    @ParameterizedTest(name = "[{index}] Aendern eines vorhandenen Kunden: id={0}")
    @ValueSource(strings = ID_UPDATE)
    @DisplayName("Aendern eines vorhandenen Kunden")
    @SuppressModernizer
    void update(final String id) {
        // given
        final var kundeId = UUID.fromString(id);
        final var kunde = repo.findById(kundeId);
        assertThat(kunde).isNotNull();
        kunde.setNachname(NEUER_NACHNAME);

        // when
        service.update(kunde, kundeId);

        // then
        final var result = repo.findById(kundeId);
        assertThat(result).isNotNull();
        assertThat(result.getNachname()).isEqualTo(NEUER_NACHNAME);
    }

    @ParameterizedTest(name = "[{index}] Loeschen eines vorhandenen Kunden: id={0}")
    @ValueSource(strings = ID_DELETE)
    @DisplayName("Loeschen eines vorhandenen Kunden")
    void deleteBysetId(final String id) {
        // given
        final var kundeId = UUID.fromString(id);

        // when
        service.deleteById(kundeId);

        // then
        final var result = repo.findById(kundeId);
        assertThat(result).isNull();
    }
}
