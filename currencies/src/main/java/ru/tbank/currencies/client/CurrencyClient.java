package ru.tbank.currencies.client;

import ru.tbank.currencies.dto.CurrencyCentralBankRequestDTO;

public interface CurrencyClient {
    CurrencyCentralBankRequestDTO getDailyRubCurrencyRates();
}
