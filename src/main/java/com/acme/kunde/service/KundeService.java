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
package com.acme.kunde.service;

import com.acme.kunde.entity.Kunde;
import com.acme.kunde.repository.KundeRepository;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/// Geschäftslogik für Kunden.
/// ![Klassendiagramm](../../../../../asciidoc/KundeService.svg)
///
/// @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)
// Maven: ![Klassendiagramm](../../../../../../generated-docs/KundeService.svg)
@Service
public class KundeService {
    private final KundeRepository repo;
    private final StableValue<Logger> logger = StableValue.of();

    /// Konstruktor mit _package private_ für _Spring_.
    ///
    /// @param repo Injiziertes Repository-Objekt.
    KundeService(final KundeRepository repo) {
        this.repo = repo;
    }

    /// Einen Kunden anhand seiner ID suchen.
    ///
    /// @param id Die Id des gesuchten Kunden
    /// @return Der gefundene Kunde
    /// @throws NotFoundException Falls kein Kunde gefunden wurde
    public Kunde findById(final UUID id) {
        getLogger().debug("findById: id={}", id);
        final var kunde = repo.findById(id);
        if (kunde == null) {
            throw new NotFoundException(id);
        }
        getLogger().debug("findById: kunde={}", kunde);
        return kunde;
    }

    /// Kunden anhand von SuchParametern als Collection suchen.
    ///
    /// @param suchparameter Die SuchParametern
    /// @return Die gefundenen Kunden
    /// @throws NotFoundException Falls keine Kunden gefunden wurden
    @SuppressWarnings({"ReturnCount", "NestedIfDepth"})
    public Collection<Kunde> find(final Map<String, List<String>> suchparameter) {
        getLogger().debug("find: suchparameter={}", suchparameter);

        final var kunden = repo.find(suchparameter);
        if (kunden.isEmpty()) {
            throw new NotFoundException(suchparameter);
        }

        getLogger().debug("find: kunden={}", kunden);
        return kunden;
    }

    /// Abfrage, welche Nachnamen es zu einem Präfix gibt.
    ///
    /// @param prefix Nachname-Präfix.
    /// @return Die passenden Nachnamen.
    /// @throws NotFoundException Falls keine Nachnamen gefunden wurden.
    public Collection<String> findNachnamenByPrefix(final String prefix) {
        final var nachnamen = repo.findNachnamenByPrefix(prefix);
        if (nachnamen.isEmpty()) {
            //noinspection NewExceptionWithoutArguments
            throw new NotFoundException();
        }
        return nachnamen;
    }

    private Logger getLogger() {
        return logger.orElseSet(() -> LoggerFactory.getLogger(KundeService.class));
    }
}
