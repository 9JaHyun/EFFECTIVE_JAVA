package ch3.Item14;

import java.util.Comparator;

public class Member implements Comparable {
    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Object o) {
        Member m = (Member) o;
        int result = name.compareTo(m.name);
        if (result == 0) {
            result = Integer.compare(age, m.age);
        }
        return result;
    }

    public int compareToV2(Member member) {
        return COMPARATOR.compare(this, member);
    }

    private static final Comparator<Member> COMPARATOR =
            Comparator.comparing((Member m) -> m.name)
                    .thenComparingInt(m -> m.age);

    static Comparator<Object> hashCodeOrderV1 = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            return o1.hashCode() - o2.hashCode();
        }
    };

    static Comparator<Object> hashCodeOrderV2 = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            return Integer.compare(o1.hashCode(), o2.hashCode());
        }
    };

    static Comparator<Object> hashCodeOrderV3 = Comparator.
            comparingInt(Object::hashCode);
}

