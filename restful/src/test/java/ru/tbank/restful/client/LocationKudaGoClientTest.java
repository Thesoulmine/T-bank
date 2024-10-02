package ru.tbank.restful.client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.mapper.LocationMapperImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class LocationKudaGoClientTest {

    @Container
    private static final WireMockContainer wireMockContainer = new WireMockContainer("wiremock/wiremock:3.6.0")
            .withMappingFromResource(
                    "location-client",
                    LocationKudaGoClientTest.class,
                    "location-client-mock-config.json");

    private static LocationKudaGoClient locationClient;

    @BeforeAll
    public static void beforeAll() {
        RestClient restClient = RestClient.create(wireMockContainer.getBaseUrl());
        locationClient = new LocationKudaGoClient(restClient, new LocationMapperImpl());
    }

    @Test
    public void getAllLocations_ReturnAllLocations() {
        Location location1 = new Location();
        location1.setSlug("qwe");
        location1.setName("qwe");

        Location location2 = new Location();
        location2.setSlug("asd");
        location2.setName("asd");

        List<Location> result = locationClient.getAllLocations();

        assertEquals(List.of(location1, location2), result);
    }
}