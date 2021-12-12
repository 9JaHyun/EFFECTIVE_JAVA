package ch4.Item24;

public class TestSample {
    public static void main(String[] args) {
        // Static Member Class의 경우 독립적으로 생성 가능.
        StaticMemberClass staticMemberClass = new StaticMemberClass();
        StaticMemberClass.InnerClass innerClass = new StaticMemberClass.InnerClass();

        staticMemberClass.doSomething();
        innerClass.doSomething();

        NonStaticMemberClass nonStaticMemberClass = new NonStaticMemberClass();
//        NonStaticMemberClass.InnerClass innerClass1 = new NonStaticMemberClass().InnerClass();    독립적으로 생성 불가

        // OuterClass의 인스턴스가 존재해야 생성할 수 있다.
        NonStaticMemberClass.InnerClass innerClass1 = nonStaticMemberClass.new InnerClass();
        innerClass1.doSomething();
        innerClass1.runOuterClassMethod();
        nonStaticMemberClass.runInnerClassDoSomething();

        MyInterface myInterface = new MyInterface() {
            @Override
            public void doSomething() {
                System.out.println("익명 클래스");
            }
        };
        myInterface.doSomething();

        LocalClass localClass = new LocalClass();
        localClass.doSomething();
    }
}
