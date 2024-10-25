package ru.tbank.restful.dto;

import lombok.Data;

import java.util.List;

@Data
public class LocationResponseDTO {

    private Long id;

    private String slug;

    private String name;

    private List<EventResponseDTO> events;
}
