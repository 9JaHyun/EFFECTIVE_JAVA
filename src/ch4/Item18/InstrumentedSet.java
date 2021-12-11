package ch4.Item18;

import java.util.Collection;
import java.util.Set;

// 래퍼 클래스
public class InstrumentedSet<E> extends ForwardingSet<E> {
    private int addCount = 0;

    // 컴포지션 클래스 => 래퍼 클래스
    public InstrumentedSet(Set<E> s) {
        super(s);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}