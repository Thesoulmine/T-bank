package ru.tbank.restful.service;

import org.springframework.stereotype.Service;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.repository.Repository;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final Repository<Location> locationRepository;

    public LocationServiceImpl(Repository<Location> locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location getLocationBy(Long id) {
        return locationRepository.find(id).orElseThrow();
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location updateLocation(Long id, Location location) {
        return locationRepository.update(id, location);
    }

    @Override
    public Location deleteLocationBy(Long id) {
        return locationRepository.delete(id);
    }
}
