package ru.tbank.restful.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.repository.LocationRepository;

import java.util.List;

@Qualifier("LocationDataBaseService")
@Service
public class LocationDataBaseService implements LocationService {

    private final LocationRepository locationRepository;

    public LocationDataBaseService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location getLocationBy(Long id) {
        return locationRepository.findById(id).orElseThrow();
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location updateLocation(Long id, Location location) {
        location.setId(id);
        return locationRepository.save(location);
    }

    @Override
    public Location deleteLocationBy(Long id) {
        return locationRepository.deleteBy(id);
    }
}
