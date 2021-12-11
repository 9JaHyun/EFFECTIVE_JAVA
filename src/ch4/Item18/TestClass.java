package ch4.Item18;

import java.util.List;

public class TestClass {
    public static void main(String[] args) {
        be_Careful_Using_Extend();
        System.out.println("");
        decorator_Pattern();
    }

    private static void be_Careful_Using_Extend() {
        InstrumentedHashSet<Object> testSet = new InstrumentedHashSet<>();
        testSet.addAll(List.of("element1", "element2", "element3"));
        System.out.println("결과는 3이여야 합니다만... " + testSet.getAddCount());
    }

    private static void decorator_Pattern() {
        InstrumentedSet wrapperClass = new InstrumentedSet(
                new ForwardingSet(new InstrumentedHashSet()));

        wrapperClass.addAll(List.of("이 패턴은", "데코레이터", "패턴이라고도", "한다"));
        int result = wrapperClass.getAddCount();
        System.out.println("결과는 4 입니다 = " + result);
    }
}
