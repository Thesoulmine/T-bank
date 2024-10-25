package ru.tbank.restful.service;

import ru.tbank.restful.entity.Event;
import ru.tbank.restful.entity.Location;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    List<Event> getAllEvents();

    List<Event> getAllEventsBy(
            String name,
            Location location,
            LocalDate fromDate,
            LocalDate toDate);

    Event getEventBy(Long id);

    Event saveEvent(Event event);

    Event updateEvent(Long id, Event event);

    Event deleteEventBy(Long id);
}
