package ru.tbank.currencies.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyRateResponseDTO {

    private String currency;

    private BigDecimal rate;
}
