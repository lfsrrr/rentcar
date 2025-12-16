-- User anlegen
CREATE USER rentcar PASSWORD 'p';

-- Tablespace anlegen (Pfad im Container!)
CREATE TABLESPACE rentcar OWNER rentcar LOCATION '/var/lib/postgresql/tablespace';

-- Datenbank anlegen
CREATE DATABASE rentcar OWNER rentcar TABLESPACE rentcar;

-- Rechte gewähren
GRANT ALL PRIVILEGES ON DATABASE rentcar TO rentcar;
