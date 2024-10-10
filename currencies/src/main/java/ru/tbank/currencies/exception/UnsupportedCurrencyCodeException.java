package ru.tbank.currencies.exception;

public class UnsupportedCurrencyCodeException extends RuntimeException {

    public UnsupportedCurrencyCodeException(String message) {
        super(message);
    }
}
