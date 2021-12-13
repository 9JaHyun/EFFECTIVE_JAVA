package ch5.Item26;

import java.util.List;

public class RowTypeAndWildCard {
    public static void main(String[] args) {
        List<String> strings = List.of("Hello", "Unbounded", "Wildcards!");
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        printList(strings);
        printList(integers);
    }

    // Raw Type
    private static void unsafeAdd(List list, Object o) {
        list.add(o);
    }

    private static void printList(List<?> list) {
        System.out.println(list);
    }
}
