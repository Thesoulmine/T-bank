package ru.tbank.restful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tbank.restful.entity.Event;
import ru.tbank.restful.service.EventService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("")
    public List<Event> getEvents(
            @RequestParam int budget,
            @RequestParam String currency,
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo) {
        
    }
}
