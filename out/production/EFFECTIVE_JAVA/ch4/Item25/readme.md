# Item25. 톱레벨 클래스(최상위 클래스)는 한 파일에 하나만 담아라
* 소스 파일 하나에 최상위 클래스를 여러 개 선언하더라도 자바 컴파일러에 동작에는 큰 무리가 없다.
* 하지만 굳이 사용자가 불편을 감수하면서까지 최상위 클래스를 여러 개 선언할 필요가 없다.
* 만약 중복되는 이름으로 클래스를 선언하는 경우 운이 좋다면 컴파일러가 잡아낼 수 있으나 그렇지 않는다면, 대참사가 발생한다.

## 결론
* 소스 파일 하나에는 반드시 톱레벨 클래스를 하나만 담자.
```java
class Utensil{
    static final String NAME = "pan";
}

class Dessert{
    static final String NAME = "cake";
}
```
가 아닌...

```java
public class Utensil{
    static final String NAME = "pan";
}
```
```java
class Dessert{
    static final String NAME = "cake";
}
```
이렇게 선언하자.

* 굳이 여러개 담으려면 정적 멤버 클래스로 담는 것을 추천한다.
```java
public class TopLevelTest {
    public static void main(String[] args) {
        // 집기(Utensil), 디저트(Dessert) 클래스가 Utensil.java 파일 하나에 정의되어 있는 경우
        System.out.println(MemberUtensil.NAME + MemberDessert.NAME);
    }

    private static class MemberUtensil {
        static final String NAME = "pan";
    }

    private static class MemberDessert {
        static final String NAME = "cake";
    }
}
```