package ch5.Item26;

import java.util.ArrayList;
import java.util.List;

public class DontUseRawType {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        unsafeAdd(strings, Integer.valueOf(10));
    }

    // Raw Type
    private static void unsafeAdd(List list, Object o) {
        list.add(o);
    }
}
