package ru.tbank.currencies.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tbank.currencies.dto.EventResponseDTO;
import ru.tbank.currencies.mapper.EventMapper;
import ru.tbank.currencies.service.EventService;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService,
                           EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping("/events")
    public List<EventResponseDTO> getEvents(
            @RequestParam Integer budget,
            @RequestParam String currency,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
            @RequestParam(required = false) LocalDate dateFrom,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
            @RequestParam(required = false) LocalDate dateTo) throws ExecutionException, InterruptedException {
        return eventMapper.toResponseDTO(
                eventService.getEventsBy(budget, currency, dateFrom, dateTo).get());
    }

    @GetMapping("/reactive/events")
    public List<EventResponseDTO> getEventsReactive(
            @RequestParam Integer budget,
            @RequestParam String currency,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
            @RequestParam(required = false) LocalDate dateFrom,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
            @RequestParam(required = false) LocalDate dateTo) {
        return eventMapper.toResponseDTO(
                eventService.getEventsReactiveBy(budget, currency, dateFrom, dateTo).block());
    }
}
