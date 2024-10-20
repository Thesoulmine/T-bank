package ru.tbank.currencies.exception;

public class InvalidCurrencyCodeException extends RuntimeException {

    public InvalidCurrencyCodeException(String message) {
        super(message);
    }
}
