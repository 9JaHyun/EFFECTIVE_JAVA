package ch3.Item12;

import java.util.Objects;

public class Member {
    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "{이름: " + name + ", 나이: " + age + "}";
    }
}

