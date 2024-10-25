package ru.tbank.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tbank.restful.entity.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location deleteLocationById(Long id);

    @Query("SELECT l FROM Location l JOIN FETCH l.events WHERE l.id = :id")
    Optional<Location> findByWithEvents(Long id);
}
