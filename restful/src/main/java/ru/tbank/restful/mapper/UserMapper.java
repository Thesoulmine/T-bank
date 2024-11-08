package ru.tbank.restful.mapper;

import org.mapstruct.Mapper;
import ru.tbank.restful.dto.UserRequestDTO;
import ru.tbank.restful.dto.UserResponseDTO;
import ru.tbank.restful.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO userRequestDTO);

    UserResponseDTO toResponseDTO(User user);
}
