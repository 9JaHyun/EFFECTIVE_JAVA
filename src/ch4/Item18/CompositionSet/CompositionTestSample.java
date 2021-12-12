package ch4.Item18.CompositionSet;

import java.util.HashSet;
import java.util.List;

public class CompositionTestSample {
    public static void main(String[] args) {
        InstrumentedHashSetByComposition<String> set = new InstrumentedHashSetByComposition<>(new HashSet<>());
        set.addAll(List.of("이 패턴은", "데코레이터", "패턴이라고도", "한다"));
        System.out.println("결과는 4 입니다 = " + set.getAddCount());
    }
}
