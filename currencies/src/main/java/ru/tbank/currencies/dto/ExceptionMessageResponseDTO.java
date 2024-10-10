package ru.tbank.currencies.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionMessageResponseDTO {

    private String code;

    private String message;
}
