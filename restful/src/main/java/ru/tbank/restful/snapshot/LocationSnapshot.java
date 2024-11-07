package ru.tbank.restful.snapshot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tbank.restful.enums.RepositoryActionType;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationSnapshot {

    private Long id;

    private String slug;

    private String name;

    private RepositoryActionType actionType;
}
