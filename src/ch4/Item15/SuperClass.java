package ch4.Item15;

// 최상위 클래스는 package-private (default) or public만 가능하다.
class SuperClass {
    String v1;

    void method1() {
        System.out.println("오버라이드는 항상 상위 클래스의 접근 범위보다 좁을 수 없다.");
    }

    private void method2() {
        System.out.println("오버라이드가 불가능한 SuperClass.method2 메서드 ");
    }

}
class SubClass extends SuperClass {

    // only public or package-private
//    @Override
//    public void method1() {
//        System.out.println("서브클래스의 method1");
//    }

    void method1() {
        System.out.println("서브클래스의 method1");
    }

//    private void method1() {
//        System.out.println("subClass에서 재정의한 method1");
//    }
}

class TestClass {
    public static void main(String[] args) {
        SuperClass superClass = new SuperClass();
        SubClass subClass1 = new SubClass();

        superClass.method1();

        subClass1.method1();
    }
}