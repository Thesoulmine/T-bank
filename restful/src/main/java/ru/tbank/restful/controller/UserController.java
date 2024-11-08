package ru.tbank.restful.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.tbank.restful.dto.UserRequestDTO;
import ru.tbank.restful.dto.UserResponseDTO;
import ru.tbank.restful.mapper.UserMapper;
import ru.tbank.restful.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final int sessionRememberedTimeout;
    private final int sessionNotRememberedTimeout;

    public UserController(UserService userService,
                          UserMapper userMapper,
                          @Value("${server.servlet.session.remembered-timeout}")
                          int sessionRememberedTimeout,
                          @Value("${server.servlet.session.not-remembered-timeout}")
                          int sessionNotRememberedTimeout) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.sessionRememberedTimeout = sessionRememberedTimeout;
        this.sessionNotRememberedTimeout = sessionNotRememberedTimeout;
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
            httpSession.setMaxInactiveInterval(sessionRememberedTimeout);
        } else {
            httpSession.setMaxInactiveInterval(sessionNotRememberedTimeout);
        }
    }

    @PostMapping("/logout")
    public void logoutUser(HttpServletRequest request) throws ServletException {
        request.logout();
    }

    @PostMapping("/test")
    public void test() {

    }

//    @PostMapping("/reset")
}
