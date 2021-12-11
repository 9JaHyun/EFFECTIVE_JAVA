package ch4.Item24;

public class InnerClassTest {
    public static void main(String[] args) {
        OuterClass.StaticMemberClass staticMemberClass = new OuterClass.StaticMemberClass();

        // 독립적으로 생성 불가
//        OuterClass.NoneStaticMemberClass noneStaticMemberClass = new OuterClass.NoneStaticMemberClass();

        // None-Static의 경우 외부 클래스 인스턴스를 통해 생성해야 한다.
        OuterClass outerClass = new OuterClass();
        OuterClass.NoneStaticMemberClass noneStaticMemberClass = outerClass.new NoneStaticMemberClass();
    }
}
