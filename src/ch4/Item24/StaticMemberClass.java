package ch4.Item24;

public class StaticMemberClass {
    private String field1;
    private static String field2;

    void doSomething() {
        field1 = "hello";
        System.out.println("Do Something by OuterClass : " + field1);
    }

    static class InnerClass{
        void doSomething() {
            field2 = "GoodBye";
            // 단순 인스턴스 필드는 접근 불가
//            System.out.println("Do Something by InnerClass : " + field1);

            // 정적 필드는 접근 가능!
            System.out.println("Do Something by InnerClass : " + field2);
        }
    }
}