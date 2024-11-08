package ru.tbank.restful.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tbank.restful.dto.UserRequestDTO;
import ru.tbank.restful.dto.UserResponseDTO;
import ru.tbank.restful.mapper.UserMapper;
import ru.tbank.restful.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public UserResponseDTO registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userMapper.toResponseDTO(
                userService.registerUser(userMapper.toEntity(userRequestDTO)));
    }

//    @PostMapping("/login")
//
//    @PostMapping("/logout")
//
//    @PostMapping("/reset")
}
