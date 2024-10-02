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
import ru.tbank.restful.dto.LocationRequestDTO;
import ru.tbank.restful.dto.LocationResponseDTO;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.mapper.LocationMapper;
import ru.tbank.restful.mapper.LocationMapperImpl;
import ru.tbank.restful.service.LocationService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
    public void getAllLocations_ReturnAllLocations() throws Exception {
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
    public void createLocation_SaveLocationAndReturnSavedLocation() throws Exception {
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
}