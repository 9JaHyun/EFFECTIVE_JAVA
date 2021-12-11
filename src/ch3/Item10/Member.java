package ch3.Item10;

public class Member {
    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Member)) {
            return false;
        }
        Member m = (Member) obj;
        return m.name == name && m.age == age;
    }
}
