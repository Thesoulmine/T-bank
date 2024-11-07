package ru.tbank.restful.repository;

import ru.tbank.restful.listener.repository.RepositorySaveEventListener;

import java.util.List;
import java.util.Optional;

public interface Repository<T> extends RepositorySaveEventListener<T> {

    Optional<T> find(Long id);

    List<T> findAll();

    T save(T entity);

    T update(Long id, T entity);

    T delete(Long id);
}
