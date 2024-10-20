package ru.tbank.currencies.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class CurrencyClientCacheTTLConfiguration {

    @CacheEvict(value = "currencies", allEntries = true)
    @Scheduled(fixedRateString = "${client.currency.cache.ttl}")
    public void refreshCurrencyClientCache() {

    }
}
