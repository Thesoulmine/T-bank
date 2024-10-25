package ru.tbank.restful.mapper;

import org.mapstruct.Mapper;
import ru.tbank.restful.dto.EventRequestDTO;
import ru.tbank.restful.dto.EventResponseDTO;
import ru.tbank.restful.entity.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event toEntity(EventResponseDTO eventResponseDTO);

    Event toEntity(EventRequestDTO eventRequestDTO);

    List<Event> toEntity(List<EventRequestDTO> eventRequestDTOList);

    EventResponseDTO toResponseDTO(Event event);

    List<EventResponseDTO> toResponseDTO(List<Event> eventList);

    EventRequestDTO toRequestDTO(Event event);
}
