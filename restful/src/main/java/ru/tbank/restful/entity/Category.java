package ru.tbank.restful.entity;

import lombok.Data;
import ru.tbank.restful.annotation.Id;

@Data
public class Category {

    @Id
    private Long id;

    private Long kudaGoId;

    private String slug;

    private String name;
}
