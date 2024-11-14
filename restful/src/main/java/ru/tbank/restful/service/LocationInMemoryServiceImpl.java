package ru.tbank.restful.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.enums.RepositoryActionType;
import ru.tbank.restful.listener.repository.RepositorySaveEventListener;
import ru.tbank.restful.publisher.RepositorySaveEventPublisher;
import ru.tbank.restful.repository.Repository;
import ru.tbank.restful.snapshot.CategorySnapshot;
import ru.tbank.restful.snapshot.ChangesHistory;
import ru.tbank.restful.snapshot.LocationSnapshot;

import java.util.*;

@Qualifier("LocationInMemoryServiceImpl")
@Service
public class LocationInMemoryServiceImpl
        implements LocationInMemoryService, RepositorySaveEventPublisher<Location>, ChangesHistory<LocationSnapshot> {

    private final Repository<Location> locationRepository;
    private final Set<RepositorySaveEventListener<Location>> listeners = new HashSet<>();
    private final Deque<LocationSnapshot> locationHistory = new ArrayDeque<>();

    public LocationInMemoryServiceImpl(Repository<Location> locationRepository) {
        this.locationRepository = locationRepository;
        addListener(locationRepository);
    }

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
        Location savedLocation = saveEntity(location);

        if (savedLocation != null) {
            addToHistory(new LocationSnapshot(
                    savedLocation.getId(),
                    savedLocation.getSlug(),
                    savedLocation.getName(),
                    RepositoryActionType.SAVE));
        }

        return savedLocation;
    }

    @Override
    public Location updateLocation(Long id, Location location) {
        Location originLocation = getLocationBy(id);

        if (originLocation != null) {
            addToHistory(new LocationSnapshot(
                    originLocation.getId(),
                    originLocation.getSlug(),
                    originLocation.getName(),
                    RepositoryActionType.UPDATE));
        }

        return locationRepository.update(id, location);
    }

    @Override
    public Location deleteLocationBy(Long id) {
        Location deletedLocation = locationRepository.delete(id);

        if (deletedLocation != null) {
            addToHistory(new LocationSnapshot(
                    deletedLocation.getId(),
                    deletedLocation.getSlug(),
                    deletedLocation.getName(),
                    RepositoryActionType.DELETE));
        }

        return deletedLocation;
    }

    @Override
    public void addToHistory(LocationSnapshot snapshot) {
        locationHistory.addFirst(snapshot);
    }

    @Override
    public void undoHistory() {
        LocationSnapshot snapshot = locationHistory.getFirst();

        switch (snapshot.getActionType()) {
            case SAVE -> {
                deleteLocationBy(snapshot.getId());
            }
            case UPDATE -> {
                Location location = new Location();
                location.setId(snapshot.getId());
                location.setSlug(snapshot.getSlug());
                location.setName(snapshot.getName());
                locationRepository.update(location.getId(), location);
            }
            case DELETE -> {
                Location location = new Location();
                location.setId(snapshot.getId());
                location.setSlug(snapshot.getSlug());
                location.setName(snapshot.getName());
                saveLocation(location);
            }
        }
    }
}
