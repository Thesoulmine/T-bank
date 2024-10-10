package ru.tbank.currencies.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Currency {

    private String fromCurrency;

    private String toCurrency;

    private BigDecimal rate;
}
