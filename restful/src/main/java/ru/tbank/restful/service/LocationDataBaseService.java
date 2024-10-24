package ru.tbank.restful.service;

import org.springframework.stereotype.Service;
import ru.tbank.restful.entity.Location;

import java.util.List;

@Service
public class LocationDataBaseService implements LocationService {
    @Override
    public List<Location> getAllLocations() {
        return List.of();
    }

    @Override
    public Location getLocationBy(Long id) {
        return null;
    }

    @Override
    public Location saveLocation(Location location) {
        return null;
    }

    @Override
    public Location updateLocation(Long id, Location location) {
        return null;
    }

    @Override
    public Location deleteLocationBy(Long id) {
        return null;
    }
}
