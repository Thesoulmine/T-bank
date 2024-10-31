package ru.tbank.restful.publisher;

import ru.tbank.restful.listener.repository.RepositorySaveEventListener;

public interface RepositorySaveEventPublisher<T> {

    void addListener(RepositorySaveEventListener<T> listener);

    void removeListener(RepositorySaveEventListener<T> listener);

    T saveEntity(T entity);
}
