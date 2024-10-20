package ru.tbank.restful.mapper;

import org.mapstruct.Mapper;
import ru.tbank.restful.dto.EventKudaGoClientResponseDTO;
import ru.tbank.restful.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event toEntity(EventKudaGoClientResponseDTO eventResponseDTO);
}
