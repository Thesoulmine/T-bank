package ru.tbank.restful.dto;

import lombok.Data;

@Data
public class CategoryResponseDTO {

    private Long id;

    private Long kudaGoId;

    private String slug;

    private String name;
}
