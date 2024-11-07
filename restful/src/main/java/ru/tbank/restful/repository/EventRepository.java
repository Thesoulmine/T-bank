package ru.tbank.restful.repository;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tbank.restful.entity.Event;
import ru.tbank.restful.entity.Event_;
import ru.tbank.restful.entity.Location;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAll(Specification<Event> specification);

    static Specification<Event> buildSpecification(
            String name,
            Location location,
            LocalDate fromDate,
            LocalDate toDate) {
        List<Specification<Event>> specifications = new ArrayList<>();

        if (location != null) {
            specifications.add((Specification<Event>) (root, query, criteriaBuilder) -> {
                root.fetch(Event_.location);
                return criteriaBuilder.conjunction();
            });
        }

        if (name != null) {
            specifications.add((Specification<Event>) (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(Event_.name), name));
        }

        if (location != null) {
            specifications.add((Specification<Event>) (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.join(Event_.location), location));
        }

        if (fromDate != null) {
            specifications.add((Specification<Event>) (root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(Event_.date), fromDate));
        }

        if (toDate != null) {
            specifications.add((Specification<Event>) (root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(Event_.date), toDate));
        }

        return specifications.stream()
                .reduce(Specification::and).orElse(null);
    }
}
