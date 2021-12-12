package ch4.Item24;

public class LocalClass {
    private String field1;
    private String field2;

    public void doSomething() {
        String localField1 = "Hello";
        String localField2 = "LocalClass";
        class DoSomethingLocalClass {
            String plusString(String str1, String str2) {
                return str1 + " " + str2;
            }
        }
        DoSomethingLocalClass localClass = new DoSomethingLocalClass();
        String s = localClass.plusString(localField1, localField2);
        System.out.println(s);
    }
}
