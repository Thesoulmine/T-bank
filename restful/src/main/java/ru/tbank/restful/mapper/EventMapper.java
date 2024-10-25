package ru.tbank.restful.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.tbank.restful.dto.EventRequestDTO;
import ru.tbank.restful.dto.EventResponseDTO;
import ru.tbank.restful.dto.LocationResponseDTO;
import ru.tbank.restful.entity.Event;
import ru.tbank.restful.entity.Location;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event toEntity(EventResponseDTO eventResponseDTO);

    @Mapping(source = "locationId", target = "location", qualifiedByName = "locationIdToLocation")
    Event toEntity(EventRequestDTO eventRequestDTO);

    List<Event> toEntity(List<EventRequestDTO> eventRequestDTOList);

    EventResponseDTO toResponseDTO(Event event);

    List<EventResponseDTO> toResponseDTO(List<Event> eventList);

    EventRequestDTO toRequestDTO(Event event);

    @Named("locationIdToLocation")
    default Location locationIdToLocation(Long locationId) {
        if (locationId == null) {
            return null;
        }
        Location location = new Location();
        location.setId(locationId);
        return location;
    }
}
