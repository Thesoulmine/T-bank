package ru.tbank.restful.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    Optional<T> find(Long id);

    List<T> findAll();

    T save(T entity);

    T update(Long id, T entity);

    T delete(Long id);
}
