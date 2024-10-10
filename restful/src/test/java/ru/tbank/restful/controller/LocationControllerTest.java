package ru.tbank.restful.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.tbank.restful.dto.ExceptionMessageResponseDTO;
import ru.tbank.restful.dto.LocationRequestDTO;
import ru.tbank.restful.dto.LocationResponseDTO;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.mapper.LocationMapperImpl;
import ru.tbank.restful.service.LocationService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Import(LocationMapperImpl.class)
@WebMvcTest(LocationController.class)
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LocationService locationService;

    @Test
    public void getAllLocations_ReturnAllLocations_Ok() throws Exception {
        Location location1 = new Location();
        location1.setId(1L);
        location1.setName("qwe");

        Location location2 = new Location();
        location2.setId(2L);
        location2.setName("asd");

        LocationResponseDTO resultLocation1 = new LocationResponseDTO();
        resultLocation1.setId(1L);
        resultLocation1.setName("qwe");

        LocationResponseDTO resultLocation2 = new LocationResponseDTO();
        resultLocation2.setId(2L);
        resultLocation2.setName("asd");

        Mockito.when(locationService.getAllLocations()).thenReturn(List.of(location1, location2));

        mockMvc.perform(get("/api/v1/locations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(List.of(resultLocation1, resultLocation2))));
    }

    @Test
    public void getLocation_ReturnLocation_Ok() throws Exception {
        Location location = new Location();
        location.setId(1L);
        location.setName("qwe");

        LocationResponseDTO resultLocation = new LocationResponseDTO();
        resultLocation.setId(1L);
        resultLocation.setName("qwe");

        Mockito.when(locationService.getLocationBy(Mockito.eq(location.getId()))).thenReturn(location);

        mockMvc.perform(get("/api/v1/locations/{id}", location.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(resultLocation)));
    }

    @Test
    public void getLocation_ThrowsNoSuchElementException_NotFound() throws Exception {
        Long id = 1L;

        ExceptionMessageResponseDTO result = new ExceptionMessageResponseDTO("Location not found");

        Mockito.when(locationService.getLocationBy(Mockito.eq(id))).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/api/v1/locations/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(result)));

    }

    @Test
    public void createLocation_SaveLocationAndReturnSavedLocation_Ok() throws Exception {
        LocationRequestDTO requestLocation = new LocationRequestDTO();
        requestLocation.setName("qwe");

        Location location = new Location();
        location.setId(1L);
        location.setName("qwe");

        LocationResponseDTO resultLocation = new LocationResponseDTO();
        resultLocation.setId(1L);
        resultLocation.setName("qwe");

        Mockito.when(locationService.saveLocation(Mockito.any(Location.class))).thenReturn(location);

        mockMvc.perform(post("/api/v1/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestLocation)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(resultLocation)));
    }

    @Test
    public void updateLocation_UpdateLocationAndReturnUpdatedLocation_Ok() throws Exception {
        LocationRequestDTO requestLocation = new LocationRequestDTO();
        requestLocation.setName("qwe");

        Location location = new Location();
        location.setId(1L);
        location.setName("qwe");

        LocationResponseDTO resultLocation = new LocationResponseDTO();
        resultLocation.setId(1L);
        resultLocation.setName("qwe");

        Mockito.when(locationService.updateLocation(Mockito.eq(location.getId()), Mockito.any(Location.class)))
                .thenReturn(location);

        mockMvc.perform(put("/api/v1/locations/{id}", location.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestLocation)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(resultLocation)));
    }

    @Test
    public void updateLocation_ThrowsNoSuchElementException_NotFound() throws Exception {
        LocationRequestDTO requestLocation = new LocationRequestDTO();
        requestLocation.setName("qwe");

        Location location = new Location();
        location.setId(1L);
        location.setName("qwe");

        ExceptionMessageResponseDTO result = new ExceptionMessageResponseDTO("Location not found");

        Mockito.when(locationService.updateLocation(Mockito.eq(location.getId()), Mockito.any(Location.class)))
                .thenThrow(NoSuchElementException.class);

        mockMvc.perform(put("/api/v1/locations/{id}", location.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestLocation)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    public void deleteLocation_DeleteLocationAndReturnDeletedLocation_Ok() throws Exception {
        Location location = new Location();
        location.setId(1L);
        location.setName("qwe");

        LocationResponseDTO resultLocation = new LocationResponseDTO();
        resultLocation.setId(1L);
        resultLocation.setName("qwe");

        Mockito.when(locationService.deleteLocationBy(Mockito.eq(location.getId()))).thenReturn(location);

        mockMvc.perform(delete("/api/v1/locations/{id}", location.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(resultLocation)));
    }
}