package ch4.Item20;

public class HelloInterfaceImpl1 implements HelloInterface{
    @Override
    public void process2() {
        System.out.println("HelloInterface1의 process2");
    }

    @Override
    public void process3() {
        System.out.println("HelloInterface1의 process3");
    }
}
