package ru.tbank.currencies.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestClient;

@Configuration
public class CurrencyClientConfiguration {

    @Qualifier("CentralBankClient")
    @Bean
    public RestClient centralBankClient(@Value("${client.currency.cb.url}") String url) {
        return RestClient.builder()
                .baseUrl(url)
                .messageConverters(converters -> converters.add(new MappingJackson2XmlHttpMessageConverter()))
                .build();
    }
}
