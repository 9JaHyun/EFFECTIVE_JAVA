package ch3.Item10;

public class EqualsTestSample {
    public static void main(String[] args) {
        Member member1 = new Member("Kim", 20);
        Member member2 = new Member("Kim", 20);

        System.out.println(member1 == member2);         // false
        System.out.println(member1.equals(member2));     // true
    }
}
