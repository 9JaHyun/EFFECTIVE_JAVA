package ch4.Item20;

public class HelloInterfaceImpl2 implements HelloInterface {
    @Override
    public void process2() {
        System.out.println("HelloInterface2의 process2");
    }

    @Override
    public void process3() {
        System.out.println("HelloInterface2의 process3");
    }
}
