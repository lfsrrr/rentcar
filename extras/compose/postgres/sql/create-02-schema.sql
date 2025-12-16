-- Schema anlegen
CREATE SCHEMA IF NOT EXISTS rentcar AUTHORIZATION rentcar;

-- Suchpfad für den User setzen (damit er 'rentcar.' nicht immer tippen muss)
ALTER ROLE rentcar SET search_path = 'rentcar';
