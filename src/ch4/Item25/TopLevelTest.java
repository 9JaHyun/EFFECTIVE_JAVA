package ch4.Item25;

// 차라리 최상위 클래스 여러개를 담고 싶다면, 정적 멤버 클래스를 담자.
public class TopLevelTest {
    public static void main(String[] args) {
        // 집기(Utensil), 디저트(Dessert) 클래스가 Utensil.java 파일 하나에 정의되어 있는 경우
        System.out.println(MemberUtensil.NAME + MemberDessert.NAME);
    }

    private static class MemberUtensil {
        static final String NAME = "pan";
    }

    private static class MemberDessert {
        static final String NAME = "cake";
    }
}

