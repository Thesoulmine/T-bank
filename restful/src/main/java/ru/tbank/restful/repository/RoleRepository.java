package ru.tbank.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tbank.restful.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
