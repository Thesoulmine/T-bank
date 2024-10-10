package ru.tbank.currencies.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.tbank.currencies.dto.CurrencyCentralBankRequestDTO;
import ru.tbank.currencies.exception.CurrencyClientUnavailableException;

@Component
public class CurrencyCentralBankClient implements CurrencyClient {

    private final RestClient restClient;

    public CurrencyCentralBankClient(@Qualifier("CentralBankClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Cacheable("currencies")
    @CircuitBreaker(name = "central-bank-client", fallbackMethod = "circuitBreakerFallback")
    @Override
    public CurrencyCentralBankRequestDTO getDailyRubCurrencyRates() {
        return restClient.get()
                .uri("XML_daily.asp")
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .body(CurrencyCentralBankRequestDTO.class);
    }

    public CurrencyCentralBankRequestDTO circuitBreakerFallback(Throwable throwable) {
        throw new CurrencyClientUnavailableException("Central bank service unavailable");
    }
}
