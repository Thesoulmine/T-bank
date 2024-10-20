package ru.tbank.currencies.mapper;

import org.mapstruct.Mapper;
import ru.tbank.currencies.dto.EventKudaGoClientResponseDTO;
import ru.tbank.currencies.dto.EventResponseDTO;
import ru.tbank.currencies.entity.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event toEntity(EventKudaGoClientResponseDTO.Event event);

    List<Event> toEntity(List<EventKudaGoClientResponseDTO.Event> events);

    EventResponseDTO toResponseDTO(Event event);

    List<EventResponseDTO> toResponseDTO(List<Event> events);
}
