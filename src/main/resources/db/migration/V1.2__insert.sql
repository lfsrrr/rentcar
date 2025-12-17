-- 1. Car Details anlegen (muss vor Car passieren wegen Foreign Key)
INSERT INTO car_details (id, version, created, updated, farbe, sitzplaetze, motor, baujahr)
VALUES
    ('10000000-0000-0000-0000-000000000001', 0, NOW(), NOW(), 'Schwarz', 5, 0, 2023),
    ('10000000-0000-0000-0000-000000000002', 0, NOW(), NOW(), 'Weiss', 5, 1, 2024);

-- 2. Autos anlegen
INSERT INTO car (id, version, created, updated, hersteller, modell, erstzulassung, kennzeichen, details_id)
VALUES
    ('20000000-0000-0000-0000-000000000001', 0, NOW(), NOW(), 'BMW', 'M4', '2023-01-15', 'KA-AB-123', '10000000-0000-0000-0000-000000000001'),
    ('20000000-0000-0000-0000-000000000002', 0, NOW(), NOW(), 'Volkswagen', 'Golf 8', '2024-03-10', 'KA-VW-456', '10000000-0000-0000-0000-000000000002');

-- 3. Kunden anlegen
INSERT INTO customer (id, version, created, updated, vorname, nachname, email, geburtsdatum)
VALUES
    ('30000000-0000-0000-0000-000000000001', 0, NOW(), NOW(), 'Max', 'Mustermann', 'max@mustermann.de', '1990-05-20'),
    ('30000000-0000-0000-0000-000000000002', 0, NOW(), NOW(), 'Erika', 'Musterfrau', 'erika@musterfrau.de', '1995-08-12');

-- 4. Mietvorgänge (Rentals) anlegen
INSERT INTO rental (id, version, created, updated, mietbeginn, mietende, gesamtpreis, customer_id, car_id)
VALUES
    ('40000000-0000-0000-0000-000000000001', 0, NOW(), NOW(), '2025-05-01', '2025-05-05', 299.50, '30000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001');

-- 5. Verknüpfungstabelle Car -> Rentals (Wichtig für OneToMany)
INSERT INTO car_rentals (car_id, rentals_id)
VALUES
    ('20000000-0000-0000-0000-000000000001', '40000000-0000-0000-0000-000000000001');
