package ru.tbank.restful.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.tbank.restful.dto.EventKudaGoClientResponseDTO;
import ru.tbank.restful.entity.Event;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class EventKudaGoClient implements EventClient {

    private final RestClient restClient;

    public EventKudaGoClient(@Qualifier("kudaGoRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Event> getEventsBy(LocalDate dateFrom, LocalDate dateTo) {
        restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("events/")
                        .queryParam("page_size", 100)
                        .queryParam("fields", "title,price,is_free,favorites_count")
                        .queryParam("order_by", "-favorites_count")
                        .queryParam("actual_since", Timestamp.valueOf(dateFrom.atTime(LocalTime.MIN)))
                        .queryParam("actual_until", Timestamp.valueOf(dateTo.atTime(LocalTime.MAX)))
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<EventKudaGoClientResponseDTO>>() {});
    }
}
