package ru.tbank;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class CustomLinkedList<E> implements CustomList<E> {

    private Node head;
    private Node tail;
    private int size;
    private int modCount = 0;

    @Override
    public boolean add(E element) {
        if (head == null) {
            head = new Node(null, null, element);
        } else {
            linkLastNode(element);
        }

        modCount++;
        size++;

        return true;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        Node temp = findNodeByIndex(index);

        return temp.value;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        Node temp = findNodeByIndex(index);
        unlinkNode(temp);
        modCount++;

        return temp.value;
    }

    @Override
    public boolean contains(E element) {
        Node temp = head;

        if (element == null) {
            while (temp != null) {
                if (temp.value == null) {
                    return true;
                }
                temp = temp.next;
            }
        } else {
            while (temp != null) {
                if (temp.value.equals(element)) {
                    return true;
                }
                temp = temp.next;
            }
        }

        return false;
    }

    @Override
    public boolean addAll(CustomList<E> list) {
        if (list == null) {
            return false;
        }

        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
            modCount++;
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
            modCount++;
        }

        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public CustomIterator<E> iterator() {
        return new CustomLinkedListIterator();
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void linkLastNode(E element) {
        if (tail == null) {
            tail = new Node(head, null, element);
            head.next = tail;
        } else {
            Node temp = tail;
            tail = new Node(temp, null, element);
            temp.next = tail;
        }
    }

    private void unlinkNode(Node node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            tail = node.previous;
        }

        size--;
    }

    private Node findNodeByIndex(int index) {
        Node temp;

        if (index < (size / 2) || size == 1) {
            temp = head;
            for (int i = 0; i < index; i++)
                temp = temp.next;
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--)
                temp = temp.previous;
        }

        return temp;
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

    private class CustomLinkedListIterator implements CustomIterator<E> {

        private Node current = head;
        private final int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public E next() {
            checkForModification();

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E value = current.value;
            current = current.next;
            return value;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            while (hasNext()) {
                checkForModification();
                action.accept(current.value);
                current = current.next;
            }
        }

        private void checkForModification() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
