package ru.tbank.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tbank.restful.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
