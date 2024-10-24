package ru.tbank.restful.service;

import org.springframework.stereotype.Service;
import ru.tbank.restful.entity.Event;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Override
    public List<Event> getAllEvents() {
        return List.of();
    }

    @Override
    public Event getEventBy(Long id) {
        return null;
    }

    @Override
    public Event saveEvent(Event event) {
        return null;
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        return null;
    }

    @Override
    public Event deleteEventBy(Long id) {
        return null;
    }
}
