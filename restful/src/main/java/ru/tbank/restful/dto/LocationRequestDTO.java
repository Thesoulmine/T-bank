package ru.tbank.restful.dto;

import lombok.Data;

import java.util.List;

@Data
public class LocationRequestDTO {

    private String slug;

    private String name;
}
