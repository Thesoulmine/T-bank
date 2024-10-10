package ru.tbank.currencies.service;

import ru.tbank.currencies.dto.CurrencyConvertResponseDTO;
import ru.tbank.currencies.dto.CurrencyRateResponseDTO;

import java.math.BigDecimal;

public interface CurrencyService {

    CurrencyRateResponseDTO getCurrencyRate(String currencyCode);

    CurrencyConvertResponseDTO convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount);
}
