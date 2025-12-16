package com.acme.rentcar.controller;

import com.acme.rentcar.entity.Car;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(target = "id", source = "id") // ID mappen
    @Mapping(target = "farbe", source = "details.farbe")
    @Mapping(target = "sitzplaetze", source = "details.sitzplaetze")
    @Mapping(target = "motor", source = "details.motor")
    CarDTO toDTO(Car car);

    List<CarDTO> toDTOs(Collection<Car> cars);
}
