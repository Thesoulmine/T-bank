package ru.tbank.restful.entity;

import lombok.Data;
import ru.tbank.restful.annotation.Id;

@Data
public class Location {

    @Id
    private Long id;

    private String slug;

    private String name;
}
