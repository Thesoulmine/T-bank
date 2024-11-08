package ru.tbank.restful.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAllWithEvents();
    }

    @Transactional
    @Override
    public Location getLocationBy(Long id) {
        return locationRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public Location getLocationWithEventsBy(Long id) {
        return locationRepository.findByWithEvents(id).orElseThrow();
    }

    @Transactional
    @Override
    public Location saveLocation(Location location) {
        location.setEvents(null);
        return locationRepository.save(location);
    }

    @Transactional
    @Override
    public Location updateLocation(Long id, Location location) {
        location.setId(id);
        location.setEvents(null);
        return locationRepository.save(location);
    }

    @Transactional
    @Override
    public Location deleteLocationBy(Long id) {
        Location location = getLocationWithEventsBy(id);
        locationRepository.deleteById(id);
        return location;
    }
}
