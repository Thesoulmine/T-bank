package ru.tbank.currencies.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrencyConvertRequestDTO {

    @NotNull(message = "fromCurrency is mandatory parameter")
    private String fromCurrency;

    @NotNull(message = "toCurrency is mandatory parameter")
    private String toCurrency;

    @NotNull(message = "amount is mandatory parameter")
    @PositiveOrZero(message = "amount must be positive digit or zero")
    private BigDecimal amount;
}
