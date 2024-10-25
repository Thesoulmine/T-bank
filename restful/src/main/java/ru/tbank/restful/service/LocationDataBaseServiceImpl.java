package ru.tbank.restful.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tbank.restful.entity.Event;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.repository.LocationRepository;

import java.util.List;

@Qualifier("LocationDataBaseServiceImpl")
@Service
public class LocationDataBaseServiceImpl implements LocationDataBaseService {

    private final LocationRepository locationRepository;

    public LocationDataBaseServiceImpl(LocationRepository locationRepository) {
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
    public Location getLocationWithEventsBy(Long id) {
        return locationRepository.findByWithEvents(id).orElseThrow();
    }

    @Override
    public Location saveLocation(Location location) {
        location.setEvents(null);
        return locationRepository.save(location);
    }

    @Override
    public Location updateLocation(Long id, Location location) {
        location.setId(id);
        location.setEvents(null);
        return locationRepository.save(location);
    }

    @Override
    public Location deleteLocationBy(Long id) {
        Location location = getLocationWithEventsBy(id);
        locationRepository.deleteById(id);
        return location;
    }
}
