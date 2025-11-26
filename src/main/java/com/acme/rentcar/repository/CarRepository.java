
package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import java.util.Collection;
import java.util.UUID;
import org.jspecify.annotations.Nullable;

/// Repository Interface ohne Optional, dafür mit JSpecify Null-Annotationen.
public interface CarRepository {

    Collection<Car> findAll();

    /// Findet ein Auto anhand der ID.
    /// @return Das Auto oder null, wenn nicht gefunden.
    @Nullable
    Car findById(UUID id);

    Collection<Car> findByHersteller(String hersteller);

    Car save(Car car);

    /// Aktualisiert ein Auto.
    /// @return Das aktualisierte Auto oder null, wenn ID nicht gefunden.
    @Nullable
    Car update(Car car);
}
