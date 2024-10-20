package ru.tbank.currencies.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.tbank.currencies.client.EventClient;
import ru.tbank.currencies.entity.Event;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EventServiceImpl implements EventService {

    private final EventClient eventClient;
    private final CurrencyService currencyService;

    public EventServiceImpl(EventClient eventClient, CurrencyService currencyService) {
        this.eventClient = eventClient;
        this.currencyService = currencyService;
    }

    @Async
    @Override
    public CompletableFuture<List<Event>> getEventsBy(Integer budget, String currency, LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom == null || dateTo == null) {
            dateFrom = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            dateTo = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        }

        LocalDate finalDateFrom = dateFrom;
        LocalDate finalDateTo = dateTo;

        return CompletableFuture
                .supplyAsync(() -> eventClient.getEventsBy(finalDateFrom, finalDateTo))
                .thenCombine(
                        CompletableFuture.supplyAsync(() ->
                                currencyService.convertCurrency(currency, "RUB", BigDecimal.valueOf(budget))
                                        .getConvertedAmount()),
                        (s1, s2) ->
                                s1.stream()
                                        .filter(element -> convertEventPriceToBigDecimal(element).compareTo(s2) <= 0)
                                        .toList());
    }

    @Override
    public Mono<List<Event>> getEventsReactiveBy(Integer budget, String currency, LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom == null || dateTo == null) {
            dateFrom = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            dateTo = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        }

        LocalDate finalDateFrom = dateFrom;
        LocalDate finalDateTo = dateTo;

        Mono<List<Event>> eventsMono = Mono.fromCallable(() ->
                eventClient.getEventsBy(finalDateFrom, finalDateTo));

        Mono<BigDecimal> convertedAmountMono = Mono.fromCallable(() ->
                currencyService.convertCurrency(currency, "RUB", BigDecimal.valueOf(budget))
                        .getConvertedAmount());

        return Mono.zip(eventsMono, convertedAmountMono)
                .flatMap(tuple ->
                        Mono.just(
                                tuple.getT1().stream()
                                        .filter(event -> convertEventPriceToBigDecimal(event).compareTo(tuple.getT2()) <= 0)
                                        .toList()));
    }

    private BigDecimal convertEventPriceToBigDecimal(Event event) {
        if (event.isFree()) {
            return BigDecimal.ZERO;
        }

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(event.getPrice());

        if (matcher.find()) {
            return BigDecimal.valueOf(Long.parseLong(matcher.group()));
        } else {
            return BigDecimal.ZERO;
        }
    }
}
