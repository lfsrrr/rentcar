-- Indizes für häufig gesuchte Spalten
CREATE INDEX idx_car_hersteller ON car(hersteller);
CREATE INDEX idx_car_kennzeichen ON car(kennzeichen);
CREATE INDEX idx_customer_email ON customer(email);
CREATE INDEX idx_customer_nachname ON customer(nachname);
