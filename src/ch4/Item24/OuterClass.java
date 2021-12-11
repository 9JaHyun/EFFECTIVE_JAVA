package ch4.Item24;

public class OuterClass {
    private String outerField1;
    private String outerField2;

    // StaticMemberClass의 경우 Static 필드만 사용할 수 있다
    private static String outerStaticField3;


    void outerProcess() {
        System.out.println("Use OuterClass");
    }

    void usingLocalClass() {
        class LocalClass{
            void localClassProcess() {
                System.out.println("Local Class Method");
            }
        }
    }

    static class StaticMemberClass {
        private String innerField1;
        private String innerField2;

        void staticMemberProcess() {
            System.out.println("Use Static Member Class");
            outerStaticField3 = "정적 클래스는 static 필드만 사용할 수 있다.";
            System.out.println(outerStaticField3);
        }
    }

    class NoneStaticMemberClass {
        private String innerField1;

        void noneStaticMemberProcess() {
            System.out.println("User None-Static Member Class");
            outerField1 = "비정적 클래스는 static 필드가 아니어도 사용할 수 있다.";
            outerField2 = "비정적 클래스는 static 필드가 아니어도 사용할 수 있다.";

            // 정규화된 this를 사용해서 바깥 인스턴스의 메서드나 참조를 가져올 수 있다.
            OuterClass.this.outerProcess();

        }
    }

}
