package ru.tbank.currencies.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Currency {

    private String fromCurrency;

    private String toCurrency;

    private BigDecimal rate;
}
