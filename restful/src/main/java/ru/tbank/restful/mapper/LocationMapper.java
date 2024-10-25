package ru.tbank.restful.mapper;

import org.mapstruct.Mapper;
import ru.tbank.restful.dto.LocationRequestDTO;
import ru.tbank.restful.dto.LocationResponseDTO;
import ru.tbank.restful.entity.Location;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location toEntity(LocationResponseDTO locationResponseDTO);

    Location toEntity(LocationRequestDTO locationRequestDTO);

    List<Location> toEntity(List<LocationRequestDTO> locationRequestDTOList);

    LocationResponseDTO toResponseDTO(Location location);

    List<LocationResponseDTO> toResponseDTO(List<Location> locationList);

    LocationRequestDTO toRequestDTO(Location location);
}
