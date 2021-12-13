package ch5.Item28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Chooser {
    private final Object[] choiceArray;

    public Chooser(Collection choices) {
        choiceArray = choices.toArray();
    }

    // choose()를 호출할 때마다 반환되는 Object를 원하는 타입으로 형변환 해야 한다.
    public Object choose() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        return choiceArray[rnd.nextInt(choiceArray.length)];
    }
}

// 제네릭으로 만들기 1
class ChooserV1<T> {
    private final T[] choiceArray;

    // 동작은 하나, 런타임에도 안전한지 보장은 못한다.
    public ChooserV1(Collection<T> choices) {
        choiceArray = (T[]) choices.toArray();
    }

    // choose()를 호출할 때마다 반환되는 Object를 원하는 타입으로 형변환 해야 한다.
    public Object choose() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        return choiceArray[rnd.nextInt(choiceArray.length)];
    }
}

// 제네릭으로 만들기 2 : 경고를 숨기는 것 보다는 리스트로 만들자
class ChooserV2<T> {
    private final List<T> choiceList;

    // 동작은 하나, 런타임에도 안전한지 보장은 못한다.
    public ChooserV2(Collection<T> choices) {
        choiceList = new ArrayList<>(choices);
    }

    // choose()를 호출할 때마다 반환되는 Object를 원하는 타입으로 형변환 해야 한다.
    public Object choose() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        return choiceList.get(rnd.nextInt(choiceList.size()));
    }
}