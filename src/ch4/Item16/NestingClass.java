package ch4.Item16;

public class NestingClass {
    public static void main(String[] args) {
        // 클래스에 종속되기 때문에 스코프는 크게 상관은 없다.
        // 대신 추상 개념을 명확하게 표현해 사용자가 햇갈리지 않게 해야 한다!
        NestedClass nestedClass1 = new NestedClass();
        String nestedFiled1 = nestedClass1.nestedField2;
        String nestedFiled2 = nestedClass1.nestedField2;

        NestedClass2 nestedClass2 = new NestedClass2();
        String nestedFiled3 = nestedClass2.nestedField3;
        String nestedFiled4 = nestedClass2.nestedField4;
    }
    private String outerField1;
    private String outerField2;

    // private or package-private 중첩 클래스의 경우 공개를 해도 상관 없다.
    private static class NestedClass{
        String nestedField1;
        String nestedField2;
    }

    static class NestedClass2 {
        String nestedField3;
        String nestedField4;
    }
}
