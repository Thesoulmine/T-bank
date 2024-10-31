package ru.tbank.restful.snapshot;

public interface ChangesHistory<T> {

    void addToHistory(T snapshot);

    void undoHistory();
}
