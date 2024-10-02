package ru.tbank.restful.dto;

import lombok.Data;

@Data
public class CategoryRequestDTO {

    private Long id;

    private String slug;

    private String name;
}
