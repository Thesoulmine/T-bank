package ru.tbank;

import java.util.List;

public interface CustomList<E> {

    boolean add(E element);

    E get(int index);

    E remove(int index);

    boolean contains(E element);

    boolean addAll(CustomList<E> list);

    boolean addAll(List<E> list);

    int size();
}
