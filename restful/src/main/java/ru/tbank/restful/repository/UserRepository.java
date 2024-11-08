package ru.tbank.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tbank.restful.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
