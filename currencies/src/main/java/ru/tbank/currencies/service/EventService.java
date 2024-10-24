package ru.tbank.currencies.service;

import reactor.core.publisher.Mono;
import ru.tbank.currencies.entity.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EventService {

    CompletableFuture<List<Event>> getEventsBy(Integer budget, String currency, LocalDate dateFrom, LocalDate dateTo);

    Mono<List<Event>> getEventsReactiveBy(Integer budget, String currency, LocalDate dateFrom, LocalDate dateTo);
}
