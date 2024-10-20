package ru.tbank.restful.service;

import org.springframework.stereotype.Service;
import ru.tbank.restful.client.EventClient;
import ru.tbank.restful.entity.Event;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventClient eventClient;

    public EventServiceImpl(EventClient eventClient) {
        this.eventClient = eventClient;
    }

    public List<Event> getEventsBy(int budget, String currency, LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }
}
