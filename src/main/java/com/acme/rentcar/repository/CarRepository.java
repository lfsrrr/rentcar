package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import java.util.Collection;
import java.util.UUID;
import org.jspecify.annotations.Nullable;

/// Repository Interface ohne Optional, dafür mit JSpecify Null-Annotationen.
///
/// Definiert die grundlegenden CRUD-Operationen für die Verwaltung von Car Objekten.
public interface CarRepository {

    /// Findet alle gespeicherten Autos.
    ///
    /// @return Eine Collection aller vorhandenen Car Instanzen.
    Collection<Car> findAll();

    /// Findet ein Auto anhand der ID.
    ///
    /// @param id Die UUID des gesuchten Autos.
    /// @return Das gefundene Car Objekt oder null, wenn es nicht existiert.
    @Nullable
    Car findById(UUID id);

    /// Sucht nach Autos eines bestimmten Herstellers.
    ///
    /// @param hersteller Der Name des Herstellers, nach dem gefiltert werden soll.
    /// @return Eine Collection von Autos, die diesem Hersteller entsprechen.
    Collection<Car> findByHersteller(String hersteller);

    /// Speichert ein neues Auto oder überschreibt ein existierendes, je nach Implementierung.
    ///
    /// @param car Das zu speichernde Car Objekt.
    /// @return Das persistierte Car Objekt.
    Car save(Car car);

    /// Aktualisiert ein bestehendes Auto.
    ///
    /// @param car Das Car Objekt mit den aktualisierten Daten.
    /// @return Das aktualisierte Car Objekt oder null, wenn die ID nicht gefunden wurde.
    @Nullable
    Car update(Car car);
}
