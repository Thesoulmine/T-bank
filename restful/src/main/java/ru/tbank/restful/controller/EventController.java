package ru.tbank.restful.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tbank.restful.dto.ExceptionMessageResponseDTO;
import ru.tbank.restful.dto.EventRequestDTO;
import ru.tbank.restful.dto.EventResponseDTO;
import ru.tbank.restful.mapper.EventMapper;
import ru.tbank.restful.service.EventService;
import ru.tbank.restful.service.EventService;
import ru.tbank.timedstarter.annotation.Timed;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService,
                           EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    public List<EventResponseDTO> getAllEvents() {
        return eventMapper.toResponseDTO(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public EventResponseDTO getEvent(@PathVariable Long id) {
        return eventMapper.toResponseDTO(eventService.getEventBy(id));
    }

    @PostMapping
    public EventResponseDTO createEvent(
            @RequestBody EventRequestDTO eventRequestDTO) {
        return eventMapper.toResponseDTO(
                eventService.saveEvent(eventMapper.toEntity(eventRequestDTO)));
    }

    @PutMapping("/{id}")
    public EventResponseDTO updateEvent(
            @PathVariable Long id,
            @RequestBody EventRequestDTO eventRequestDTO) {
        return eventMapper.toResponseDTO(
                eventService.updateEvent(id, eventMapper.toEntity(eventRequestDTO)));
    }

    @DeleteMapping("/{id}")
    public EventResponseDTO deleteEvent(@PathVariable Long id) {
        return eventMapper.toResponseDTO(eventService.deleteEventBy(id));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionMessageResponseDTO> handleNoSuchElementException() {
        return new ResponseEntity<>(
                new ExceptionMessageResponseDTO("Event not found"),
                HttpStatus.NOT_FOUND);
    }
}
