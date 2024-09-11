package ru.tbank.list;

import java.util.List;

public class CustomLinkedList<E> implements CustomList<E> {

    private Node head;
    private Node tail;
    private int size;

    @Override
    public boolean add(E element) {
        if (head == null) {
            head = new Node(null, null, element);
        } else {
            if (tail == null) {
                tail = new Node(head, null, element);
                head.next = tail;
            } else {
                Node temp = tail;
                tail = new Node(temp, null, element);
                temp.next = tail;
            }
        }
        size++;

        return true;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        Node temp = head;

        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }

        return temp.value;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        Node temp = head;

        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }

        if (temp.previous != null) {
            temp.previous.next = temp.next;
        } else {
            head = temp.next;
        }

        if (temp.next != null) {
            temp.next.previous = temp.previous;
        } else {
            tail = temp.previous;
        }

        size--;

        return temp.value;
    }

    @Override
    public boolean contains(E element) {
        Node temp = head;

        while (temp != null) {
            if (element.equals(temp.value)) {
                return true;
            }
            temp = temp.next;
        }

        return false;
    }

    @Override
    public boolean addAll(CustomList<E> list) {
        if (list == null) {
            return false;
        }

        for (int i = 0; i < list.getSize(); i++) {
            add(list.get(i));
        }

        return true;
    }

    @Override
    public boolean addAll(List<E> list) {
        if (list == null) {
            return false;
        }

        for (E element : list) {
            add(element);
            size++;
        }

        return true;
    }

    @Override
    public int getSize() {
        return size;
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class Node {
        Node previous;
        Node next;
        E value;

        public Node(Node previous, Node next, E value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }
    }
}
