package ru.tbank.restful.service;

import ru.tbank.restful.entity.Location;

import java.util.List;

public interface LocationInMemoryService {

    List<Location> getAllLocations();

    Location getLocationBy(Long id);

    Location saveLocation(Location location);

    Location updateLocation(Long id, Location location);

    Location deleteLocationBy(Long id);
}
