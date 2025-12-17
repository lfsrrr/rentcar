package com.acme.rentcar.controller;

import com.acme.rentcar.entity.Car;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/// Mapper für die Konvertierung zwischen der Entity [Car] und dem Datentransferobjekt [CarDTO].
///
/// Nutzt MapStruct, um die Implementierung zur Compile-Zeit zu generieren.
/// Durch `componentModel = "spring"` wird der Mapper automatisch als Spring-Bean bereitgestellt
/// und kann per Dependency Injection verwendet werden.
@Mapper(componentModel = "spring")
public interface CarMapper {

    /// Konvertiert eine [Car]-Entity in ein [CarDTO].
    ///
    /// Hierbei findet ein "Flattening" statt: Die Eigenschaften aus dem eingebetteten
    /// `details`-Objekt (Farbe, Sitzplätze, Motor) werden direkt auf die Felder des DTOs gemappt.
    ///
    /// @param car Die zu konvertierende Entity.
    /// @return Das resultierende DTO mit flacher Struktur.
    @Mapping(target = "id", source = "id")
    @Mapping(target = "farbe", source = "details.farbe")
    @Mapping(target = "sitzplaetze", source = "details.sitzplaetze")
    @Mapping(target = "motor", source = "details.motor")
    CarDTO toDTO(Car car);

    /// Konvertiert eine Collection von Entities in eine Liste von DTOs.
    ///
    /// @param cars Die Collection der [Car]-Entities.
    /// @return Eine Liste von [CarDTO]-Objekten.
    List<CarDTO> toDTOs(Collection<Car> cars);
}
