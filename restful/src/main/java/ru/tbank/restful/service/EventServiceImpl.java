package ru.tbank.restful.service;

import org.springframework.stereotype.Service;
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

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getAllEventsBy(
            String name,
            Long locationId,
            LocalDate fromDate,
            LocalDate toDate) {
        Location location = locationDataBaseService.getLocationBy(locationId);

        return eventRepository.findAll(
                EventRepository.buildSpecification(name, location, fromDate, toDate));
    }

    @Override
    public Event getEventBy(Long id) {
        return eventRepository.findById(id).orElseThrow();
    }

    @Override
    public Event saveEvent(Event event) {
        //locationDataBaseService.getLocationBy(event.getLocation().getId());
        System.out.println(event);
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        event.setId(id);
        return eventRepository.save(event);
    }

    @Override
    public Event deleteEventBy(Long id) {
        return eventRepository.deleteEventById(id);
    }
}
