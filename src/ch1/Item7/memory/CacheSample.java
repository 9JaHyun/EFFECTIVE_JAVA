package ch1.Item7.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class CacheSample {
    public static void main(String[] args) {
        cache();
    }

    private static void cache() {
        Object key1 = new Object();
        Object value1 = new Object();

        Map<Object, Object> cache = new HashMap<>();
        cache.put(key1, value1);
    }

    private static void weakHashMap() {
        Object key1 = new Object();
        Object value1 = new Object();

        Map<Object, Object> cache = new WeakHashMap<>();
        cache.put(key1, value1);
    }

}
