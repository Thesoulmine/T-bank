package ru.tbank.restful.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.tbank.restful.dto.LocationRequestDTO;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.limiter.RateLimiter;
import ru.tbank.restful.mapper.LocationMapper;

import java.util.List;

@Component
public class LocationKudaGoClient implements LocationClient {

    private final RestClient restClient;
    private final LocationMapper locationMapper;
    private final RateLimiter rateLimiter;

    public LocationKudaGoClient(@Qualifier("kudaGoRestClient") RestClient restClient,
                                LocationMapper locationMapper,
                                @Qualifier("KudaGoRateLimiter") RateLimiter rateLimiter) {
        this.restClient = restClient;
        this.locationMapper = locationMapper;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public List<Location> getAllLocations() {
        try {
            rateLimiter.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<LocationRequestDTO> response;

        try {
            response = restClient
                    .get()
                    .uri("locations/")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } finally {
            rateLimiter.release();
        }

        return locationMapper.toEntity(response);
    }
}
