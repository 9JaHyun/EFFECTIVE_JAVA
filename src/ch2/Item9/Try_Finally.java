package ch2.Item9;

public class Try_Finally implements AutoCloseable{
    public void doSomething() {
        System.out.println("do something");
        throw new FirstException();
    }

    @Override
    public void close() throws Exception {
        System.out.println("clear resource");
        throw new SecondException();
    }
}
