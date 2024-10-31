package ru.tbank.currencies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.tbank.currencies.dto.CurrencyConvertRequestDTO;
import ru.tbank.currencies.exception.CurrencyClientUnavailableException;
import ru.tbank.currencies.exception.InvalidCurrencyCodeException;
import ru.tbank.currencies.exception.UnsupportedClientCurrencyCodeException;
import ru.tbank.currencies.service.CurrencyService;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurrencyService currencyService;

    @Test
    public void convertCurrency_ReturnBadRequest_WhenInvalidRequestParameters() throws Exception {
        CurrencyConvertRequestDTO requestDTO =
                new CurrencyConvertRequestDTO(null, "USD", new BigDecimal(100));

        mockMvc.perform(post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        requestDTO = new CurrencyConvertRequestDTO("RUB", null, new BigDecimal(100));

        mockMvc.perform(post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        requestDTO = new CurrencyConvertRequestDTO("RUB", "USD", null);

        mockMvc.perform(post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        requestDTO = new CurrencyConvertRequestDTO("RUB", "USD", new BigDecimal(-100));

        mockMvc.perform(post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void convertCurrency_ReturnBadRequest_WhenInvalidCurrencyCode() throws Exception {
        String toCurrency = "PYG";

        CurrencyConvertRequestDTO requestDTO =
                new CurrencyConvertRequestDTO("RUB", toCurrency, new BigDecimal(100));

        Mockito.when(currencyService.convertCurrency(Mockito.any(), Mockito.eq(toCurrency), Mockito.any()))
                .thenThrow(InvalidCurrencyCodeException.class);

        mockMvc.perform(post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getCurrencyRate_ReturnBadRequest_WhenInvalidCurrencyCode() throws Exception {
        String currencyCode = "PYG";

        Mockito.when(currencyService.getCurrencyRate(Mockito.eq(currencyCode)))
                .thenThrow(InvalidCurrencyCodeException.class);

        mockMvc.perform(get("/currencies/rates/{code}", currencyCode))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void convertCurrency_NotFound_WhenUnsupportedCurrencyCode() throws Exception {
        String toCurrency = "UST";

        CurrencyConvertRequestDTO requestDTO =
                new CurrencyConvertRequestDTO("RUB", toCurrency, new BigDecimal(100));

        Mockito.when(currencyService.convertCurrency(Mockito.any(), Mockito.eq(toCurrency), Mockito.any()))
                .thenThrow(UnsupportedClientCurrencyCodeException.class);

        mockMvc.perform(post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getCurrencyRate_NotFound_WhenUnsupportedCurrencyCode() throws Exception {
        String currencyCode = "UST";

        Mockito.when(currencyService.getCurrencyRate(Mockito.eq(currencyCode)))
                .thenThrow(UnsupportedClientCurrencyCodeException.class);

        mockMvc.perform(get("/currencies/rates/{code}", currencyCode))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void convertCurrency_ServiceUnavailable_WhenCurrencyClientUnavailable() throws Exception {
        String toCurrency = "USD";

        CurrencyConvertRequestDTO requestDTO =
                new CurrencyConvertRequestDTO("RUB", toCurrency, new BigDecimal(100));

        Mockito.when(currencyService.convertCurrency(Mockito.any(), Mockito.eq(toCurrency), Mockito.any()))
                .thenThrow(CurrencyClientUnavailableException.class);

        mockMvc.perform(post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isServiceUnavailable())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.RETRY_AFTER, "3600"));
    }

    @Test
    public void getCurrencyRate_ServiceUnavailable_WhenCurrencyClientUnavailable() throws Exception {
        String currencyCode = "USD";

        Mockito.when(currencyService.getCurrencyRate(Mockito.eq(currencyCode)))
                .thenThrow(CurrencyClientUnavailableException.class);

        mockMvc.perform(get("/currencies/rates/{code}", currencyCode))
                .andExpect(MockMvcResultMatchers.status().isServiceUnavailable())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.RETRY_AFTER, "3600"));

    }
}