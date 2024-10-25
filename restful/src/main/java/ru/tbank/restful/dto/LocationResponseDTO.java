package ru.tbank.restful.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class LocationResponseDTO {

    private Long id;

    private String slug;

    private String name;

    private List<EventResponseDTO> events;

    @Data
    public static class EventResponseDTO {

        private Long id;

        private String name;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate date;
    }
}
