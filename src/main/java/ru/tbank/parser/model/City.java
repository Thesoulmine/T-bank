package ru.tbank.parser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    private String slug;

    private Coords coords;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Coords {

        private BigDecimal lat;

        private BigDecimal lon;
    }
}

