package ru.tbank.restful.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tbank.restful.dto.ExceptionMessageResponseDTO;
import ru.tbank.restful.dto.LocationRequestDTO;
import ru.tbank.restful.dto.LocationResponseDTO;
import ru.tbank.restful.mapper.LocationMapper;
import ru.tbank.restful.service.LocationService;
import ru.tbank.timedstarter.annotation.Timed;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Timed
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    public LocationController(LocationService locationService,
                              LocationMapper locationMapper) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
    }

    @GetMapping
    public ResponseEntity<List<LocationResponseDTO>> getAllLocations() {
        return new ResponseEntity<>(
                locationMapper.toResponseDTO(locationService.getAllLocations()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> getLocation(@PathVariable Long id) {
        return new ResponseEntity<>(
                locationMapper.toResponseDTO(locationService.getLocationBy(id)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LocationResponseDTO> createLocation(
            @RequestBody LocationRequestDTO locationRequestDTO) {
        return new ResponseEntity<>(
                locationMapper.toResponseDTO(
                        locationService.saveLocation(locationMapper.toEntity(locationRequestDTO))),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> updateLocation(
            @PathVariable Long id,
            @RequestBody LocationRequestDTO locationRequestDTO) {
        return new ResponseEntity<>(
                locationMapper.toResponseDTO(
                        locationService.updateLocation(id, locationMapper.toEntity(locationRequestDTO))),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> deleteLocation(@PathVariable Long id) {
        return new ResponseEntity<>(
                locationMapper.toResponseDTO(locationService.deleteLocationBy(id)),
                HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionMessageResponseDTO> handleNoSuchElementException() {
        return new ResponseEntity<>(
                new ExceptionMessageResponseDTO("Location not found"),
                HttpStatus.NOT_FOUND);
    }
}
