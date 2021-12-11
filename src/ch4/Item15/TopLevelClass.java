package ch4.Item15;

// 최상위 클래스는 package-private (default) or public만 가능하다.
class TopLevelClass {
    String v1;

    void method1() {
        System.out.println("오버라이드는 항상 상위 클래스의 접근 범위보다 좁을 수 없다.");
    }

}
class SubClass1 extends TopLevelClass {

    // only public or package-private
    @Override
    public void method1() {
        System.out.println("서브클래스의 method1");
    }

    private void method2() {
        System.out.println("내부 동작 메서드");
    }
}

class TestClass {
    public static void main(String[] args) {
        TopLevelClass topLevelClass = new TopLevelClass();
        SubClass1 subClass1 = new SubClass1();

        TopLevelClass topLevelClass2 = new SubClass1();
//        SubClass1 subClass2 = new TopLevelClass();    false

        topLevelClass.method1();
        subClass1.method1();
        topLevelClass2.method1();

        System.out.println((subClass1 instanceof TopLevelClass ));
        System.out.println((subClass1 instanceof SubClass1 ));

        System.out.println((topLevelClass instanceof TopLevelClass ));
        System.out.println((topLevelClass instanceof SubClass1 ));  // false
    }
}