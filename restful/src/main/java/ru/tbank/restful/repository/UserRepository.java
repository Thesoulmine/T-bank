package ru.tbank.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tbank.restful.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
