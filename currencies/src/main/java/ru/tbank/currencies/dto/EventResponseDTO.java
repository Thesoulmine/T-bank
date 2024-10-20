package ru.tbank.currencies.dto;

import lombok.Data;

@Data
public class EventResponseDTO {

    private String title;

    private String price;

    private boolean isFree;

    private int favoritesCount;
}
