package ch2.Item3;

public class SingletonTestSample {
    public static void main(String[] args) {
        BadSingleton badSingleton = new BadSingleton();

        System.out.println(badSingleton.equals(BadSingleton.getInstance()));

//        GoodSingleton goodSingleton = new GoodSingleton(); 컴파일 에러!
        GoodSingleton instance1 = GoodSingleton.getInstance();
        GoodSingleton instance2 = GoodSingleton.getInstance();
        System.out.println(instance1.equals(instance2));
    }
}
