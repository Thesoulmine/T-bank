package ru.tbank.currencies.client;

import ru.tbank.currencies.entity.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventClient {

    List<Event> getEventsBy(LocalDate dateFrom, LocalDate dateTo);
}
