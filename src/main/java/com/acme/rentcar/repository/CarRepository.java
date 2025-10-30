package com.acme.rentcar.repository;

import com.acme.rentcar.entity.Car;
import java.util.Collection;

// Im echten Projekt würde dies JpaRepository oder CrudRepository erweitern.
// Für den Meilenstein reicht ein einfaches Interface.
public interface CarRepository {

    Collection<Car> findAll();

}
