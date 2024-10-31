package ru.tbank.restful.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tbank.restful.entity.Event;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocationDataBaseService locationDataBaseService;

    public EventServiceImpl(EventRepository eventRepository,
                            LocationDataBaseService locationDataBaseService) {
        this.eventRepository = eventRepository;
        this.locationDataBaseService = locationDataBaseService;
    }

    @Transactional
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Transactional
    @Override
    public List<Event> getAllEventsBy(
            String name,
            Long locationId,
            LocalDate fromDate,
            LocalDate toDate) {
        Location location = null;

        if (locationId != null) {
            location = locationDataBaseService.getLocationBy(locationId);
        }

        return eventRepository.findAll(
                EventRepository.buildSpecification(name, location, fromDate, toDate));
    }

    @Transactional
    @Override
    public Event getEventBy(Long id) {
        return eventRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public Event saveEvent(Event event) {
        event.setLocation(
                locationDataBaseService.getLocationBy(event.getLocation().getId()));
        return eventRepository.save(event);
    }

    @Transactional
    @Override
    public Event updateEvent(Long id, Event event) {
        event.setId(id);
        event.setLocation(
                locationDataBaseService.getLocationBy(event.getLocation().getId()));
        return eventRepository.save(event);
    }

    @Transactional
    @Override
    public Event deleteEventBy(Long id) {
        Event event = getEventBy(id);
        eventRepository.deleteById(id);
        return event;
    }
}
