package ch4.Item24;

public class NonStaticMemberClass {
    private String field1;

    void doSomething() {
        field1 = "goodBye";
        System.out.println("Do Something by NonStaticMemberClass : " + field1);
    }

    void runInnerClassDoSomething() {
        InnerClass innerClass = new InnerClass();
        innerClass.doSomething();
    }

    // 비정적 멤버 클래스
    class InnerClass{
        void doSomething() {
            field1 = "hello";
            System.out.println("Do Something by InnerClass : " + field1);
        }

        // 정규화된 This를 통해 바깥 클래스를 호출
        void runOuterClassMethod() {
            NonStaticMemberClass.this.doSomething();
        }
    }
}