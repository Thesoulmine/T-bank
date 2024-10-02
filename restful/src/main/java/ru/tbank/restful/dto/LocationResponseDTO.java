package ru.tbank.restful.dto;

import lombok.Data;

@Data
public class LocationResponseDTO {

    private Long id;

    private String slug;

    private String name;
}
