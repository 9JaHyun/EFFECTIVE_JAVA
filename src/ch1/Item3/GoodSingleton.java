package ch1.Item3;

public class GoodSingleton {
    private static GoodSingleton INSTANCE = getInstance();

    private GoodSingleton(){}

    public static GoodSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GoodSingleton();
        }
        return INSTANCE;
    }
}
