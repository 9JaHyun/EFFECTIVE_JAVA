package ch1.Item3;

public class BadSingleton {
    private static BadSingleton INSTANCE = getInstance();

    public BadSingleton(){}

    public static BadSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BadSingleton();
        }
        return INSTANCE;
    }
}
