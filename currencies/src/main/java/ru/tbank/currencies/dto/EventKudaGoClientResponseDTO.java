package ru.tbank.currencies.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EventKudaGoClientResponseDTO {

    private List<Event> results;

    @Data
    public static class Event {

        private String title;

        private String price;

        @JsonProperty("is_free")
        private boolean isFree;

        @JsonProperty("favorites_count")
        private int favoritesCount;
    }
}
