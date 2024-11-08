package ru.tbank.restful.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tbank.restful.entity.User;
import ru.tbank.restful.exception.UserAlreadyExistException;
import ru.tbank.restful.repository.RoleRepository;
import ru.tbank.restful.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: %s".formatted(user.getEmail()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleRepository.findByName("USER").orElseThrow()));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("There is not account with that email address: %s".formatted(username)));
    }
}
