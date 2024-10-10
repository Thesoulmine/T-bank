package ru.tbank.currencies.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyConvertResponseDTO {

    private String fromCurrency;

    private String toCurrency;

    private BigDecimal convertedAmount;
}
