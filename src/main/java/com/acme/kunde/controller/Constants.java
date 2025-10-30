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
package com.acme.kunde.controller;

/// Konstante für die REST-Schnittstelle.
///
/// @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)
final class Constants {
    /// Aktuelle Versionsnummer
    static final String VERSION_1 = "1.0.0+";

    /// Basispfad für die REST-Schnittstelle.
    static final String API_PATH = "api";

    /// Muster für eine UUID.
    static final String ID_PATTERN = "[\\da-f]{8}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{12}";

    /// Version-Header für Swagger
    static final String X_VERSION = "X-Version";

    /// Aktuelle Versionsnummer als Beispiel für Swagger
    static final String VERSION_1_EXAMPLE = "1.0.0";

    private Constants() {
        // Leerer Konstruktor
    }
}
