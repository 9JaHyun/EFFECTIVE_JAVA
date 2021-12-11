package ch4.Item20;

public class RelateTest {
    public static void main(String[] args) {
        abstractClassTest();
        System.out.println();
        interfaceTest();
    }

    private static void abstractClassTest() {
        AbstractClass subClass1 = new SubClass1();
        AbstractClass subClass2 = new SubClass2();

        subClass1.process1();
        subClass1.process2();
        subClass1.process3();

        subClass2.process1();
        subClass2.process2();
        subClass2.process3();
    }

    private static void interfaceTest() {
        HelloInterface implClass1 = new HelloInterfaceImpl1();
        HelloInterface implClass2 = new HelloInterfaceImpl2();

        implClass1.process();
        implClass1.process2();
        implClass1.process3();

        implClass2.process();
        implClass2.process2();
        implClass2.process3();
    }

}
