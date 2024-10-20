package ru.tbank.currencies.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class EventClientConfiguration {

    @Bean
    @Qualifier("kudaGoRestClient")
    public RestClient kudaGoRestClient() {
        return RestClient.create("https://kudago.com/public-api/v1.4/");
    }
}
