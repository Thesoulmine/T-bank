package ru.tbank.currencies.exception;

public class CurrencyClientUnavailableException extends RuntimeException {

    public CurrencyClientUnavailableException(String message) {
        super(message);
    }
}
