package ru.tbank.restful.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.listener.repository.RepositorySaveEventListener;
import ru.tbank.restful.publisher.RepositorySaveEventPublisher;
import ru.tbank.restful.repository.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Qualifier("LocationInMemoryServiceImpl")
@Service
public class LocationInMemoryServiceImpl implements LocationInMemoryService, RepositorySaveEventPublisher<Location> {

    private final Repository<Location> locationRepository;
    private final Set<RepositorySaveEventListener<Location>> listeners = new HashSet<>();

    @Override
    public void addListener(RepositorySaveEventListener<Location> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(RepositorySaveEventListener<Location> listener) {
        listeners.remove(listener);
    }

    @Override
    public Location saveEntity(Location entity) {
        Location location = entity;

        for (RepositorySaveEventListener<Location> listener : listeners) {
            location = listener.save(entity);
        }

        return location;
    }

    public LocationInMemoryServiceImpl(Repository<Location> locationRepository) {
        this.locationRepository = locationRepository;
        addListener(locationRepository);
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
        return saveEntity(location);
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
