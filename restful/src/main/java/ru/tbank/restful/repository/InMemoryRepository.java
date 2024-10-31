package ru.tbank.restful.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Scope("prototype")
@Component
public class InMemoryRepository<T> extends RepositoryWithId<T> {

    private final Map<Long, T> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<T> find(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public T save(T entity) {
        if (idField == null) {
            initializeIdField(entity);
        }

        Long id = idCounter.getAndIncrement();
        entity = setIdFieldValueInEntity(entity, id);
        storage.put(id, entity);

        return entity;
    }

    @Override
    public T update(Long id, T entity) {
        if (!storage.containsKey(id)) {
            throw new NoSuchElementException();
        }

        setIdFieldValueInEntity(entity, id);
        storage.put(id, entity);

        return entity;
    }

    @Override
    public T delete(Long id) {
        return storage.remove(id);
    }
}
