package ru.tbank.currencies.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.tbank.currencies.dto.CurrencyConvertRequestDTO;
import ru.tbank.currencies.dto.CurrencyConvertResponseDTO;
import ru.tbank.currencies.dto.CurrencyRateResponseDTO;
import ru.tbank.currencies.dto.ExceptionMessageResponseDTO;
import ru.tbank.currencies.exception.CurrencyClientUnavailableException;
import ru.tbank.currencies.exception.InvalidCurrencyCodeException;
import ru.tbank.currencies.exception.UnsupportedClientCurrencyCodeException;
import ru.tbank.currencies.service.CurrencyService;

@Tag(name = "Currency controller", description = "Currency operations")
@ApiResponse(responseCode = "200", useReturnTypeSchema = true)
@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @ApiResponse(responseCode = "400",
            description = "Invalid currency code",
            content = @Content(schema = @Schema(implementation = ExceptionMessageResponseDTO.class)))
    @ApiResponse(responseCode = "404",
            description = "Unsupported currency code by currency service",
            content = @Content(schema = @Schema(implementation = ExceptionMessageResponseDTO.class)))
    @ApiResponse(responseCode = "503",
            description = "Currency service unavailable",
            content = @Content(schema = @Schema(implementation = ExceptionMessageResponseDTO.class)))
    @Operation(summary = "Getting rate by currency code")
    @GetMapping("/rates/{code}")
    public CurrencyRateResponseDTO getCurrencyRate(@PathVariable String code) {
        return currencyService.getCurrencyRate(code);
    }

    @ApiResponse(responseCode = "400",
            description = "Invalid parameters",
            content = @Content(schema = @Schema(implementation = ExceptionMessageResponseDTO.class)))
    @ApiResponse(responseCode = "404",
            description = "Unsupported currency code by currency service",
            content = @Content(schema = @Schema(implementation = ExceptionMessageResponseDTO.class)))
    @ApiResponse(responseCode = "503",
            description = "Currency service unavailable",
            content = @Content(schema = @Schema(implementation = ExceptionMessageResponseDTO.class)))
    @Operation(summary = "Converting between 2 currencies")
    @PostMapping("/convert")
    public CurrencyConvertResponseDTO convertCurrency(@Valid @RequestBody CurrencyConvertRequestDTO requestDTO) {
        return currencyService.convertCurrency(
                requestDTO.getFromCurrency(), requestDTO.getToCurrency(), requestDTO.getAmount());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, InvalidCurrencyCodeException.class})
    public ExceptionMessageResponseDTO badRequestHandler(Exception exception) {
        return new ExceptionMessageResponseDTO("400", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UnsupportedClientCurrencyCodeException.class)
    public ExceptionMessageResponseDTO notFoundHandler(Exception exception) {
        return new ExceptionMessageResponseDTO("404", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(CurrencyClientUnavailableException.class)
    public ExceptionMessageResponseDTO serviceUnavailableHandler(Exception exception, HttpServletResponse response) {
        response.addHeader("Retry-After", "3600");
        return new ExceptionMessageResponseDTO("503", exception.getMessage());
    }
}
