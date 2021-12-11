package ch4.Item20;

abstract class AbstractClass {
    public void process1() {
        System.out.println("추상 클래스 process1");
    }

    abstract public void process2();

    abstract public void process3();
}

class SubClass1 extends AbstractClass {

    @Override
    public void process2() {
        System.out.println("SubClass1의 process2");
    }

    @Override
    public void process3() {
        System.out.println("SubClass1의 process3");
    }
}

class SubClass2 extends AbstractClass {

    @Override
    public void process2() {
        System.out.println("SubClass2의 process2");
    }

    @Override
    public void process3() {
        System.out.println("SubClass2의 process3");
    }
}