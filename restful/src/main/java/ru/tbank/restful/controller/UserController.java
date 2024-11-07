package ru.tbank.restful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tbank.restful.dto.UserRequestDTO;
import ru.tbank.restful.dto.UserResponseDTO;
import ru.tbank.restful.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        
    }

    @PostMapping("/login")

    @PostMapping("/logout")

    @PostMapping("/reset")
}
