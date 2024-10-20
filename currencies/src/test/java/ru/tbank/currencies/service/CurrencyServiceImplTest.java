package ru.tbank.currencies.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tbank.currencies.client.CurrencyClient;
import ru.tbank.currencies.dto.CurrencyConvertResponseDTO;
import ru.tbank.currencies.dto.CurrencyRateResponseDTO;
import ru.tbank.currencies.entity.Currency;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Mock
    private CurrencyClient currencyClient;

    @Test
    public void getCurrencyRate_ReturnCurrencyRate() {
        String currencyCode = "USD";

        Currency currency = new Currency("RUB", currencyCode, new BigDecimal(96));

        CurrencyRateResponseDTO resultCurrencyDTO =
                new CurrencyRateResponseDTO(currencyCode, new BigDecimal(96));

        when(currencyClient.getDailyRubCurrencyRates()).thenReturn(List.of(currency));

        CurrencyRateResponseDTO result = currencyService.getCurrencyRate(currencyCode);

        assertEquals(resultCurrencyDTO, result);
    }

    @Test
    public void convertCurrency_ReturnConvertedCurrency() {
        String fromCurrency = "USD";
        String toCurrency = "RUB";
        BigDecimal amount = new BigDecimal(100);

        Currency currency = new Currency("RUB", fromCurrency, new BigDecimal(96));

        CurrencyConvertResponseDTO resultCurrencyDTO =
                new CurrencyConvertResponseDTO(fromCurrency, toCurrency, amount.multiply(currency.getRate()));

        when(currencyClient.getDailyRubCurrencyRates()).thenReturn(List.of(currency));

        CurrencyConvertResponseDTO result = currencyService.convertCurrency(fromCurrency, toCurrency, amount);

        assertEquals(resultCurrencyDTO, result);
    }
}