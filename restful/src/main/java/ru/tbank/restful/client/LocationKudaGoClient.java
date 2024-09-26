package ru.tbank.restful.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.tbank.restful.dto.LocationRequestDTO;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.mapper.LocationMapper;

import java.util.List;

@Component
public class LocationKudaGoClient implements LocationClient {

    private final RestClient restClient;
    private final LocationMapper locationMapper;

    public LocationKudaGoClient(@Qualifier("kudaGoRestClient") RestClient restClient,
                                LocationMapper locationMapper) {
        this.restClient = restClient;
        this.locationMapper = locationMapper;
    }

    @Override
    public List<Location> getAllLocations() {
        return locationMapper.toEntity(
                restClient
                        .get()
                        .uri("locations/")
                        .retrieve()
                        .body(new ParameterizedTypeReference<List<LocationRequestDTO>>() {}));
    }
}
