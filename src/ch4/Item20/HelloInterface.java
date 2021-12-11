package ch4.Item20;

public interface HelloInterface {
    default void process() {
        System.out.println("JAVA8의 새로운 default 기능!");
    }

    void process2();
    void process3();
}
