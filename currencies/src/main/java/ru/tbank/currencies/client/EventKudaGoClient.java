package ru.tbank.currencies.client;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.tbank.currencies.dto.EventKudaGoClientResponseDTO;
import ru.tbank.currencies.entity.Event;
import ru.tbank.currencies.mapper.EventMapper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Component
public class EventKudaGoClient implements EventClient {

    @Qualifier("kudaGoRestClient")
    private final RestClient restClient;

    private final EventMapper eventMapper;

    @Override
    public List<Event> getEventsBy(LocalDate dateFrom, LocalDate dateTo) {
        EventKudaGoClientResponseDTO response = restClient
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
                .body(EventKudaGoClientResponseDTO.class);

        return eventMapper.toEntity(Objects.requireNonNull(response).getResults());
    }
}
