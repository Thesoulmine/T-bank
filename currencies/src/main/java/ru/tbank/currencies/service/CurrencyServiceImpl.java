package ru.tbank.currencies.service;

import org.springframework.stereotype.Service;
import ru.tbank.currencies.client.CurrencyClient;
import ru.tbank.currencies.dto.CurrencyCentralBankRequestDTO.Valute;
import ru.tbank.currencies.dto.CurrencyConvertResponseDTO;
import ru.tbank.currencies.dto.CurrencyRateResponseDTO;
import ru.tbank.currencies.exception.UnsupportedClientCurrencyCodeException;
import ru.tbank.currencies.exception.UnsupportedCurrencyCodeException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Currency;
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

        List<Valute> valutes = currencyClient.getDailyRubCurrencyRates().getValutes();
        Valute valute = getValuteInByOrElseThrow(valutes, currencyCode);

        return new CurrencyRateResponseDTO(currencyCode, valute.getVunitRate());
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

    private Valute getValuteInByOrElseThrow(List<Valute> valutes, String currencyCode) {
        if (currencyCode.equals("RUB")) {
            return new Valute("RUB", BigDecimal.ONE);
        }

        return valutes.stream()
                .filter(element -> element.getCharCode().equals(currencyCode))
                .findFirst().orElseThrow(() -> new UnsupportedClientCurrencyCodeException("Unsupported client currency code"));
    }

    private void verifyCurrencyCode(String currencyCode) {
        try {
            Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException exception) {
            throw new UnsupportedCurrencyCodeException("Unsupported currency code");
        }
    }

    private BigDecimal calculateConvertedAmount(String fromCurrency, String toCurrency, BigDecimal amount) {
        List<Valute> valutes = currencyClient.getDailyRubCurrencyRates().getValutes();
        Valute toValute = getValuteInByOrElseThrow(valutes, toCurrency);
        Valute fromValute = getValuteInByOrElseThrow(valutes, fromCurrency);

        return amount.multiply(fromValute.getVunitRate())
                .divide(toValute.getVunitRate(), new MathContext(5));
    }
}
