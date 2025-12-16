-- Tabellen erstellen
CREATE TABLE car_details (
    id UUID NOT NULL PRIMARY KEY,
    version INTEGER NOT NULL,
    created TIMESTAMP,
    updated TIMESTAMP,
    farbe VARCHAR(255),
    sitzplaetze INTEGER NOT NULL,
    motor SMALLINT,
    baujahr INTEGER
);

CREATE TABLE car (
    id UUID NOT NULL PRIMARY KEY,
    version INTEGER NOT NULL,
    created TIMESTAMP,
    updated TIMESTAMP,
    hersteller VARCHAR(255),
    modell VARCHAR(255),
    erstzulassung DATE,
    kennzeichen VARCHAR(255),
    details_id UUID REFERENCES car_details(id)
);

CREATE TABLE customer (
    id UUID NOT NULL PRIMARY KEY,
    version INTEGER NOT NULL,
    created TIMESTAMP,
    updated TIMESTAMP,
    vorname VARCHAR(255),
    nachname VARCHAR(255),
    email VARCHAR(255),
    geburtsdatum DATE
);

CREATE TABLE rental (
    id UUID NOT NULL PRIMARY KEY,
    version INTEGER NOT NULL,
    created TIMESTAMP,
    updated TIMESTAMP,
    mietbeginn DATE,
    mietende DATE,
    gesamtpreis NUMERIC(19, 2),
    car_id UUID,
    customer_id UUID REFERENCES customer(id)
);

CREATE TABLE car_rentals (
    car_id UUID NOT NULL REFERENCES car(id),
    rentals_id UUID NOT NULL UNIQUE REFERENCES rental(id)
);
