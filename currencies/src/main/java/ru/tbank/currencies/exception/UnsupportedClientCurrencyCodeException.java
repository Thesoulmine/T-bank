package ru.tbank.currencies.exception;

public class UnsupportedClientCurrencyCodeException extends RuntimeException {

    public UnsupportedClientCurrencyCodeException(String message) {
        super(message);
    }
}
