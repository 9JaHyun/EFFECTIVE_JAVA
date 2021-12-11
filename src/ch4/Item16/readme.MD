# Item 16. public 클래스에서는 필드가 아닌 접근자 메서드를 사용하라.
* 이전 Item을 기억하는가? public 클래스에의 필드는 항상 두가지로 나누어 표현하라고 했다.
  * 불변이라고 확신하는 경우 `public static final`
  * 그 외 모든 필드 `private`
<br></br>
* 이번 장에서는 `private`필드로 작성을 했다면 이를 어떻게 외부에서 활용하는 지를 알아볼 것이다.

## 접근자 메서드 활용
* 접근자 메서드는 **패키지 밖에서 접근할 수 있는 방법을 제시**해주는 코드이다.
* 접근자 메서드는 여러 방법으로 구현할 수 있다.
### 자바 빈즈 규약 (Getter, Setter)
* `getter`, `setter` 메서드를 활용해서 `private 필드`들을 활용할 수 있다.

```java
public class GetterAndSetter {
  private String name;
  private int age;

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
```
* 이런 식으로 패키지 바깥에서 접근할 수 있는 클래스에게 `접근자`를 제공해 내부 표현 방식을 바꿀 수 있는 `유연성`을 제공한다.

### package-private, private 중첩 클래스
* package-private, private 중첩 클래스의 경우에는 필드를 노출해도 아무런 문제가 발생하지 않는다.
  * 클라이언트 코드가 클래스의 내부 표현에 종속되기 때문.
  * 이 클라이언트 코드도 어짜피 해당 패키지 내에서만 작동하기 때문에 걱정하지 않아도 된다.
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
    String nestedField1;    
    String nestedField2;
  }

  static class NestedClass2 {
    String nestedField3;    
    String nestedField4;
  }
}

```
* 단, 추상 개념을 올바르게 표현해주어야 한다. -> 사용자가 이해할 수 있어야 한다.

## 결론
* public 클래스는 절대 가변 필드를 직접 노출해서는 안된다. (`public static final`만 허용)
* 하지만 `package-private, private 중첩 클래스`에서는 필드를 노출하는 편이 나을 때도 있다.