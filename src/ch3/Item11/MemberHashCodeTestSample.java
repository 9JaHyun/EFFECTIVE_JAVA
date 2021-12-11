package ch3.Item11;

import java.util.*;

public class MemberHashCodeTestSample {
    public static void main(String[] args) {
        Member member1 = new Member("Kim", 20);
        Member member2 = new Member("Park", 30);
        Member member3 = new Member("Park", 30);

        HashMap<Member, Integer> hashMap = new HashMap<>();

        hashMap.put(member1, 1);
        hashMap.put(member2, 2);
        hashMap.put(member3, 3);

        System.out.println(hashMap.values());
    }
}
