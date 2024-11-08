package ru.tbank.restful.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/login")
    public void loginUser(@RequestBody UserRequestDTO userRequestDTO,
                                     HttpSession httpSession,
                                     HttpServletRequest request) throws ServletException {
        request.login(userRequestDTO.getEmail(), userRequestDTO.getPassword());
        if (userRequestDTO.isRememberMe()) {
            httpSession.setMaxInactiveInterval(2592000);
        } else {
            httpSession.setMaxInactiveInterval(1800);
        }
    }

    @PostMapping("/logout")
    public void logoutUser(HttpServletRequest request) throws ServletException {
        request.logout();
    }

//    @PostMapping("/reset")
}
