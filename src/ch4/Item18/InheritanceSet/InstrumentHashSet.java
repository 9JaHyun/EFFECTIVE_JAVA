package ch4.Item18.InheritanceSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class InstrumentHashSet<E> extends HashSet {
    public static void main(String[] args) {
        InstrumentHashSet<String> s = new InstrumentHashSet<>();
        s.addAll(List.of("A", "AAA", "B"));
        System.out.println(s.getAddCount());
    }
    private int addCount = 0;

    public InstrumentHashSet() {}

    public InstrumentHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public boolean add(Object o) {
        addCount++;
        return super.add(o);
    }

    @Override
    public boolean addAll(Collection c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount(){
        return addCount;
    }
}
