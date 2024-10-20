package ru.tbank.currencies.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tbank.currencies.deserializer.BigDecimalDeserializer;

import java.math.BigDecimal;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "ValCurs")
public class CurrencyCentralBankRequestDTO {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Valute")
    private List<Valute> valutes;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Valute {

        @JacksonXmlProperty(localName = "CharCode")
        private String charCode;

        @JsonDeserialize(using = BigDecimalDeserializer.class)
        @JacksonXmlProperty(localName = "VunitRate")
        private BigDecimal vunitRate;
    }
}


