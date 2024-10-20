package ru.tbank.currencies.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.tbank.currencies.dto.CurrencyCentralBankRequestDTO;
import ru.tbank.currencies.entity.Currency;
import ru.tbank.currencies.exception.CurrencyClientUnavailableException;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Component
public class CurrencyCentralBankClient implements CurrencyClient {

    @Qualifier("CentralBankClient")
    private final RestClient restClient;

    @Cacheable("currencies")
    @CircuitBreaker(name = "central-bank-client", fallbackMethod = "circuitBreakerFallback")
    @Override
    public List<Currency> getDailyRubCurrencyRates() {
        CurrencyCentralBankRequestDTO requestDTO = restClient.get()
                .uri("XML_daily.asp")
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .body(CurrencyCentralBankRequestDTO.class);

        return Objects.requireNonNull(requestDTO).getValutes().stream()
                .map(element -> new Currency("RUB", element.getCharCode(), element.getVunitRate()))
                .toList();
    }

    public List<Currency> circuitBreakerFallback(Throwable throwable) {
        throw new CurrencyClientUnavailableException("Central bank service unavailable");
    }
}
