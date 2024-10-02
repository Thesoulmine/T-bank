package ru.tbank;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();
        CustomLinkedList<Integer> testCustomLinkedList = new CustomLinkedList<>();
        List<Integer> testList = List.of(6, 7, 8);

        testCustomLinkedList.add(1);
        customLinkedList.add(1);
        customLinkedList.get(0);
        customLinkedList.remove(0);
        customLinkedList.contains(1);
        customLinkedList.addAll(testList);
        customLinkedList.addAll(testCustomLinkedList);

        CustomLinkedList<Integer> streamLinkedList =
                Stream.of(6, 7, 8)
                        .reduce(new CustomLinkedList<>(),
                                (list, element) -> {
                                    list.add(element);
                                    return list;
                                },
                                (list1, list2) -> {
                                    list1.addAll(list2);
                                    return list1;
                                }
                        );
    }
}
