package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import java.util.Collection;
import java.util.UUID;
import org.jspecify.annotations.Nullable;
import org.springframework.data.repository.Repository;

/// Repository für die Verwaltung von Car-Entities.
///
/// Verwendet das Basis-Interface `Repository` statt `JpaRepository`, um
/// die Verwendung von `Optional` zu vermeiden und stattdessen mit
/// `@Nullable` (JSpecify) zu arbeiten.
@org.springframework.stereotype.Repository
public interface CarRepository extends Repository<Car, UUID> {

    /// Findet alle gespeicherten Autos.
    ///
    /// @return Eine Collection aller vorhandenen Car Instanzen.
    Collection<Car> findAll();

    /// Findet ein Auto anhand der ID.
    ///
    /// Wir verwenden hier `findCarById` statt `findById`, da Spring Data JPA
    /// für `findById` standardmäßig `Optional` erzwingt.
    ///
    /// @param id Die UUID des gesuchten Autos.
    /// @return Das gefundene Car Objekt oder null, wenn es nicht existiert.
    @Nullable
    Car findCarById(UUID id);

    /// Sucht nach Autos eines bestimmten Herstellers (Case-Insensitive).
    ///
    /// @param hersteller Der Name des Herstellers.
    /// @return Eine Collection von Autos.
    Collection<Car> findByHerstellerIgnoreCase(String hersteller);

    /// Speichert ein neues Auto oder überschreibt ein existierendes.
    ///
    /// @param car Das zu speichernde Car Objekt.
    /// @return Das persistierte Car Objekt.
    Car save(Car car);
}
