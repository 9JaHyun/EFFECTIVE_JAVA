# Item 16. public 클래스에서는 필드가 아닌 접근자 메서드를 사용하라
클래스에서 필드의 접근자를 설정하는 방식은 크게 두가지 입니다.
  * 불변이라고 확신하는 경우 -> `public static final`
  * 그 외 모든 필드 -> `private`
<br></br>

두가지를 보면 알겠지만, 기본적으로 필드는 `private`하게 설정을 하고, 불변이라고 확신하는 경우에만 `public`을 허용하고 있습니다.
이렇게 해야 클래스에서 곧바로 클래스 내부 정보에 접근을 할 수 없어 캡슐화의 이점을 제공할 수 있기 때문입니다.  
  
그렇다면, `private`한 내부 정보들은 어떤 방식으로 접근을 할 수 있을까요? 이번에는 그 방법에 대해 알아봅시다!

## 접근자 메서드 활용
접근자 메서드는 **패키지 밖에서 접근할 수 있는 방법을 제시**해주는 코드입니다. 접근자 메서드는 여러 방법으로 구현할 수 있지만
가장 대표적인 방법이 바로 자바 빈즈 규약에 따른 접근자 메서드입니다
.
### 자바 빈즈 규약 (Getter, Setter)
`getter`, `setter` 메서드를 활용해서 private 필드에 접근하는 방법입니다.

```java
public class GetterAndSetter {
    private String name;
    private int age;

    // getter
    public String getName() {
      return name;
    }
  
    public int getAge() {
      return age;
    }
  
    // setter
    public void setName(String name) {
      this.name = name;
    }
  
    public void setAge(int age) {
      this.age = age;
    }
}
```

## package-private, private 중첩 클래스
package-private, private 중첩 클래스의 경우에는 필드를 노출해도 아무런 문제가 발생하지 않습니다. 왜냐하면 어짜피 그 클래스에 접근할 수 있는
곳은 중첩 클래스를 가지고 있는 클래스 뿐이니깐요!
```java
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
      // 공개 필드들
      public String nestedField1;    
      public String nestedField2;
    }
  
    static class NestedClass2 {
      public String nestedField3;    
      public String nestedField4;
    }
}
```
* public한 필드여도 클래스 자체가 종속되어있기 때문에 상관이 없다.
* 이 클라이언트 코드도 어짜피 해당 패키지 내에서만 작동하기 때문에 걱정하지 않아도 된다.
* 단, 추상 개념을 올바르게 표현해주어 사용자가 이해할 수 있어야 한다.

## 결론
* public 클래스는 절대 가변 필드를 직접 노출해서는 안된다. (`public static final`만 허용)
* 하지만 `package-private, private 중첩 클래스`에서는 필드를 노출하는 편이 나을 때도 있다.