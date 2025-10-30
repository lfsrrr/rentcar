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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.acme.kunde.repository;

import com.acme.kunde.entity.InteresseType;
import com.acme.kunde.entity.Kunde;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import static com.acme.kunde.repository.MockDB.KUNDEN;
import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;

/// Repository für den DB-Zugriff bei Kunden.
///
/// @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)
@Repository
@SuppressWarnings("PMD.UseEnumCollections")
public class KundeRepository {
    private final StableValue<Logger> logger = StableValue.of();

    /// Konstruktor mit _package private_ für Spring.
    KundeRepository() {
        // leerer Konstruktor fuer Spring
    }

    /// Einen Kunden anhand seiner ID suchen.
    /// ```
    /// SELECT *
    /// FROM   kunde
    /// WHERE  id = ...
    /// ```
    ///
    /// @param id Die Id des gesuchten Kunden
    /// @return Gefundener Kunden oder null
    @Nullable
    public Kunde findById(final UUID id) {
        getLogger().debug("findById: id={}", id);
        final var result = KUNDEN.stream()
            .filter(kunde -> Objects.equals(kunde.getId(), id))
            .findFirst()
            .orElse(null);
        getLogger().debug("findById: result={}", result);
        return result;
    }

    /// Kunden anhand von suchparameter ermitteln.
    /// Z.B. mit `GET https://localhost:8443/api?nachname=A&plz=7`
    ///
    /// @param suchparameter suchparameter.
    /// @return Gefundene Kunden oder leere Collection.
    @SuppressWarnings({"ReturnCount", "JavadocLinkAsPlainText", "PMD.AvoidLiteralsInIfCondition"})
    public Collection<Kunde> find(final Map<String, ? extends List<String>> suchparameter) {
        getLogger().debug("find: suchparameter={}", suchparameter);

        if (suchparameter.isEmpty()) {
            return findAll();
        }

        if (suchparameter.size() == 1) {
            final var nachnamen = suchparameter.get("nachname");
            if (nachnamen != null && nachnamen.size() == 1) {
                final var kunden = findByNachname(nachnamen.getFirst());
                getLogger().debug("find (nachname): kunden={}", kunden);
                return kunden;
            }

            final var emails = suchparameter.get("email");
            if (emails != null && emails.size() == 1) {
                final var kunde = findByEmail(emails.getFirst());
                getLogger().debug("find (email): kunde={}", kunde);
                return kunde == null ? emptyList() : List.of(kunde);
            }
        }
        final var interessen = suchparameter.get("interesse");
        if (interessen != null) {
            final var kunden = findByInteressen(interessen);
            getLogger().debug("find (interessen): kunden={}", kunden);
            return kunden;
        }

        getLogger().debug("find: ungueltige Suchparameter={}", suchparameter);
        return emptyList();
    }

    /// Alle Kunden als Collection ermitteln, wie sie später auch von der DB kommen.
    /// ```
    /// SELECT *
    /// FROM   kunde
    /// ```
    ///
    /// @return Alle Kunden
    private Collection<Kunde> findAll() {
        return KUNDEN;
    }

    /// Kunde zu gegebener Emailadresse aus der DB ermitteln.
    /// ```
    /// SELECT *
    /// FROM   kunde
    /// WHERE  email = ...
    /// ```
    ///
    /// @param email Emailadresse für die Suche
    /// @return Gefundener Kunde oder null
    @Nullable
    private Kunde findByEmail(final CharSequence email) {
        getLogger().debug("findByEmail: {}", email);
        final var result = KUNDEN.stream()
            .filter(kunde -> kunde.getEmail().contentEquals(email))
            .findFirst()
            .orElse(null);
        getLogger().debug("findByEmail: result={}", result);
        return result;
    }

    /// Abfrage, ob es einen Kunden mit gegebener Emailadresse gibt.
    /// ```
    /// SELECT id
    /// FROM   kunde
    /// WHERE  email = ...
    /// ```
    ///
    /// @param email Emailadresse für die Suche
    /// @return true, falls es einen solchen Kunden gibt, sonst false
    public boolean isEmailExisting(final CharSequence email) {
        getLogger().debug("isEmailExisting: email={}", email);
        final var count = KUNDEN.stream()
            .filter(kunde -> kunde.getEmail().contentEquals(email))
            .count();
        getLogger().debug("isEmailExisting: count={}", count);
        return count > 0L;
    }

    /// Kunden anhand des Nachnamens suchen.
    /// ```
    /// SELECT *
    /// FROM   kunde
    /// WHERE  nachname LIKE ...
    /// ```
    ///
    /// @param nachname Der (Teil-) Nachname der gesuchten Kunden
    /// @return Die gefundenen Kunden oder eine leere Collection
    private Collection<Kunde> findByNachname(final CharSequence nachname) {
        getLogger().debug("findByNachname: nachname={}", nachname);
        final var kunden = KUNDEN.stream()
            .filter(kunde -> kunde.getNachname().contains(nachname))
            .toList();
        getLogger().debug("findByNachname: kunden={}", kunden);
        return kunden;
    }

    /// Kunden anhand von Interessen suchen.
    /// ```
    /// SELECT *
    /// FROM   kunde
    /// WHERE  interesse = ...
    /// ```
    ///
    /// @param interessenStr Die Interessen der gesuchten Kunden
    /// @return Die gefundenen Kunden oder eine leere Collection
    private Collection<Kunde> findByInteressen(final Collection<String> interessenStr) {
        getLogger().debug("findByInteressen: interessenStr={}", interessenStr);
        final var interessen = interessenStr
            .stream()
            .map(InteresseType::of)
            .toList();
        if (interessen.contains(null)) {
            getLogger().debug("findByInteressen: keine Kunden");
            return emptyList();
        }

        getLogger().trace("findByInteressen: interessen={}", interessen);
        final var kunden = KUNDEN.stream()
            .filter(kunde -> {
                @SuppressWarnings("SetReplaceableByEnumSet")
                final Collection<InteresseType> kundeInteressen = new HashSet<>(kunde.getInteressen());
                return kundeInteressen.containsAll(interessen);
            })
            .toList();
        getLogger().debug("findByInteressen: kunden={}", kunden);
        return kunden;
    }

    /// Abfrage, welche Nachnamen es zu einem Präfix gibt.
    /// ```
    /// SELECT DISTINCT nachname
    /// FROM   kunde
    /// WHERE  nachname LIKE ...
    /// ```
    ///
    /// @param prefix Nachname-Präfix.
    /// @return Die passenden Nachnamen oder eine leere Collection.
    public Collection<String> findNachnamenByPrefix(final String prefix) {
        getLogger().debug("findByNachname: prefix={}", prefix);
        final var nachnamen = KUNDEN.stream()
            .map(Kunde::getNachname)
            .filter(nachname -> nachname.startsWith(prefix))
            .distinct()
            .toList();
        getLogger().debug("findByNachname: nachnamen={}", nachnamen);
        return nachnamen;
    }

    /// Einen neuen Kunden anlegen.
    /// ```
    /// INSERT INTO kunde
    /// VALUES ...
    /// ```
    ///
    /// @param kunde Das Objekt des neu anzulegenden Kunden.
    /// @return Der neu angelegte Kunde mit generierter ID
    public Kunde create(final Kunde kunde) {
        getLogger().debug("create: {}", kunde);
        kunde.setId(randomUUID());
        KUNDEN.add(kunde);
        getLogger().debug("create: kunde={}", kunde);
        return kunde;
    }

    /// Einen vorhandenen Kunden aktualisieren.
    /// ```
    /// UPDATE kunde
    /// SET    ...
    /// ```
    ///
    /// @param kunde Das Objekt mit den neuen Daten
    public void update(final Kunde kunde) {
        getLogger().debug("update: {}", kunde);
        final var index = IntStream
            .range(0, KUNDEN.size())
            .filter(i -> Objects.equals(KUNDEN.get(i).getId(), kunde.getId()))
            .findFirst();
        getLogger().trace("update: index={}", index);
        if (index.isEmpty()) {
            return;
        }
        KUNDEN.set(index.getAsInt(), kunde);
        getLogger().debug("update: kunde={}", kunde);
    }

    /// Einen vorhandenen Kunden löschen.
    /// ```
    /// DELETE
    /// FROM   kunde
    /// WHERE  id = ...
    /// ```
    ///
    /// @param id Die ID des zu löschenden Kunden.
    public void deleteById(final UUID id) {
        getLogger().debug("deleteById: id={}", id);
        final var index = IntStream
            .range(0, KUNDEN.size())
            .filter(i -> Objects.equals(KUNDEN.get(i).getId(), id))
            .findFirst();
        getLogger().trace("deleteById: index={}", index);
        index.ifPresent(KUNDEN::remove);
        getLogger().debug("deleteById: #KUNDEN={}", KUNDEN.size());
    }

    private Logger getLogger() {
        return logger.orElseSet(() -> LoggerFactory.getLogger(KundeRepository.class));
    }
}
