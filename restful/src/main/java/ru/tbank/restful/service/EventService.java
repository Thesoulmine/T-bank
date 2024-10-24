package ru.tbank.restful.service;

import ru.tbank.restful.entity.Event;

import java.util.List;

public interface EventService {

    List<Event> getAllEvents();

    Event getEventBy(Long id);

    Event saveEvent(Event event);

    Event updateEvent(Long id, Event event);

    Event deleteEventBy(Long id);
}
