package ru.tbank.currencies.service;

import org.springframework.stereotype.Service;
import ru.tbank.currencies.client.CurrencyClient;
import ru.tbank.currencies.dto.CurrencyConvertResponseDTO;
import ru.tbank.currencies.dto.CurrencyRateResponseDTO;
import ru.tbank.currencies.entity.Currency;
import ru.tbank.currencies.exception.InvalidCurrencyCodeException;
import ru.tbank.currencies.exception.UnsupportedClientCurrencyCodeException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyClient currencyClient;

    public CurrencyServiceImpl(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    @Override
    public CurrencyRateResponseDTO getCurrencyRate(String currencyCode) {
        verifyCurrencyCode(currencyCode);

        List<Currency> currencyRates = currencyClient.getDailyRubCurrencyRates();
        Currency currencyRate = getValuteInByOrElseThrow(currencyRates, currencyCode);

        return new CurrencyRateResponseDTO(currencyCode, currencyRate.getRate());
    }

    @Override
    public CurrencyConvertResponseDTO convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount) {
        verifyCurrencyCode(fromCurrency);
        verifyCurrencyCode(toCurrency);

        if (fromCurrency.equals(toCurrency)) {
            return new CurrencyConvertResponseDTO(fromCurrency, toCurrency, amount);
        }

        return new CurrencyConvertResponseDTO(
                fromCurrency, toCurrency, calculateConvertedAmount(fromCurrency, toCurrency, amount));
    }

    private Currency getValuteInByOrElseThrow(List<Currency> currencyRates, String currencyCode) {
        if (currencyCode.equals("RUB")) {
            return new Currency("RUB", "RUB", BigDecimal.ONE);
        }

        return currencyRates.stream()
                .filter(element -> element.getToCurrency().equals(currencyCode))
                .findFirst().orElseThrow(() -> new UnsupportedClientCurrencyCodeException("Unsupported client currency code"));
    }

    private void verifyCurrencyCode(String currencyCode) {
        try {
            java.util.Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException exception) {
            throw new InvalidCurrencyCodeException("Invalid currency code");
        }
    }

    private BigDecimal calculateConvertedAmount(String fromCurrency, String toCurrency, BigDecimal amount) {
        List<Currency> currencyRates = currencyClient.getDailyRubCurrencyRates();
        Currency toCurrencyRate = getValuteInByOrElseThrow(currencyRates, toCurrency);
        Currency fromCurrencyRate = getValuteInByOrElseThrow(currencyRates, fromCurrency);

        return amount.multiply(fromCurrencyRate.getRate())
                .divide(toCurrencyRate.getRate(), new MathContext(5));
    }
}
