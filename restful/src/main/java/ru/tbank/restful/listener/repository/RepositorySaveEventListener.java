package ru.tbank.restful.listener.repository;

public interface RepositorySaveEventListener<T> {

    T save(T entity);
}
