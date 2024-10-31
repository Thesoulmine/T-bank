package ru.tbank.restful.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tbank.restful.dto.ExceptionMessageResponseDTO;
import ru.tbank.restful.dto.LocationRequestDTO;
import ru.tbank.restful.dto.LocationResponseDTO;
import ru.tbank.restful.mapper.LocationMapper;
import ru.tbank.restful.service.LocationDataBaseService;
import ru.tbank.timedstarter.annotation.Timed;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Timed
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationDataBaseService locationDataBaseService;
    private final LocationMapper locationMapper;

    public LocationController(LocationDataBaseService locationDataBaseService,
                              LocationMapper locationMapper) {
        this.locationDataBaseService = locationDataBaseService;
        this.locationMapper = locationMapper;
    }

    @GetMapping
    public List<LocationResponseDTO> getAllLocations() {
        return locationMapper.toResponseDTO(locationDataBaseService.getAllLocations());
    }

    @GetMapping("/{id}")
    public LocationResponseDTO getLocation(@PathVariable Long id) {
        return locationMapper.toResponseDTO(locationDataBaseService.getLocationWithEventsBy(id));
    }

    @PostMapping
    public LocationResponseDTO createLocation(
            @RequestBody LocationRequestDTO locationRequestDTO) {
        return locationMapper.toResponseDTO(
                locationDataBaseService.saveLocation(locationMapper.toEntity(locationRequestDTO)));
    }

    @PutMapping("/{id}")
    public LocationResponseDTO updateLocation(
            @PathVariable Long id,
            @RequestBody LocationRequestDTO locationRequestDTO) {
        return locationMapper.toResponseDTO(
                locationDataBaseService.updateLocation(id, locationMapper.toEntity(locationRequestDTO)));
    }

    @DeleteMapping("/{id}")
    public LocationResponseDTO deleteLocation(@PathVariable Long id) {
        return locationMapper.toResponseDTO(locationDataBaseService.deleteLocationBy(id));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionMessageResponseDTO> handleNoSuchElementException() {
        return new ResponseEntity<>(
                new ExceptionMessageResponseDTO("Location not found"),
                HttpStatus.NOT_FOUND);
    }
}
