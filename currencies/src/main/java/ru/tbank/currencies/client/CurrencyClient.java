package ru.tbank.currencies.client;

import ru.tbank.currencies.entity.Currency;

import java.util.List;

public interface CurrencyClient {

    List<Currency> getDailyRubCurrencyRates();
}
