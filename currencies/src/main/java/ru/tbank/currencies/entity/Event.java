package ru.tbank.currencies.entity;

import lombok.Data;

@Data
public class Event {

    private String title;

    private String price;

    private boolean isFree;

    private int favoritesCount;
}
