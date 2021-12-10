# Item6. 불필요한 객체 생성을 피하라
만약 너무나도 자주 사용되는 자원이 있으면 그 자원은 계속해서 생성하지 말고 한번 생성해서 계속해서 사용하는 편이 좋습니다.
이를 적극적으로 활용한 것이 바로 싱글턴 패턴었고, 예시로는 JDBC나 WAS의 커넥션풀이 있죠.  
  
그렇다면 어떤 경우에 재사용을 채택해야 할까요?

## 재사용을 해야 하는 경우
### 1. 자주 쓰이는 경우
처음에 말했다시피 자주 쓰이는 경우에는 해당 자원을 재사용을 고려해야 합니다. **이 조건은 기본적으로 깔고 들어가기 때문**에 다른 케이스를 살펴볼 때
1번의 경우에 해당하는지 꼭 확인해야 합니다!

### 2. 자원 생성 비용이 비싼 경우
#### 정규표현식
자주 쓰이면서, **해당 자원을 생성하는 비용이 비싸다면 재사용을 고려**해야 합니다. 해당 도서에서는 정규표현식을 예시로 들고 있네요!
```java
public class Validator {
    static boolean isRomanNumeral(String s) {
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})" +
                "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }
}
```
정규표현식을 가장 쉽게 사용 하는 방법`String.matches`은 `Pattern.matches`를 호출합니다. 문제는 `Pattern.matches`내부에 있습니다!
```java
public final class Pattern {
    ...
    public static boolean matches(String regex, CharSequence input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }
    ...
}
```
문제는 매칭을 확인하기 위해 `Pattern.compile()`하는 비용이 매우 비싸다는 것입니다! 이 경우에는 매번 compile()을 실행하는 것 보다는 재사용을 하는 편이 좋습니다.
```java
public class Validator {
    private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})" +
            "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

    static boolean isRomanNumeral(String s) {
        return ROMAN.matcher(s).matches();
    }
}
```
책에서는 속도가 약 6.5배나 빨라졌다고 하네요!
  
## 오토박싱
이제 재사용을 벗어나서 다른 불필요한 객체 생성 중 하나인 **오토박싱**에 대해 알아봅시다.
오토박싱이 사용자에게 유용한 기능은 맞으나, 성능면으로는 그렇게 좋은 방법이 아닙니다.

```java
public class AutoBoxing{
    private static long sum() {
        Long sum = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        return sum;
    }
}
```
해당 코드는 기본형 타입 `long`이 아닌 그의 참조 타입인 `Long`를 통해 연산을 진행하고 있습니다. 이 경우에는 불필요한 Long 타입이 2^31개가 만들어지고 이를 다시 오토박싱해야 하는 상황에 처하게 됩니다.
그렇기 때문에 만약 참조 타입이 필요가 없다면 이를 변형해주어야 합니다!
```java
public class AutoBoxing{
    private static long sumV2() {
        long sum = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        return sum;
    }
}
```
이 역시 속도가 후자의 경우에 체감이 될 정도로 빨라지게 됩니다.

## 주의점
이번 장에서의 핵심은 **불필요한** 객체 생성을 피하라고 한 것이지, **객체 생성을 피하라**라는 것이 아닙니다!
점점 JVM의 성능이 향샹되고 있기 때문에, 일반적인 객체 생성과 회수의 경우에는 큰 부담이 되지 않습니다. 오히려 객체 풀을 만드는 방식이 더 큰 부담으로 다가올 수 있습니다!  

## 결론
* 객체의 생성비용이 큰 무거운 객체의 경우에는 재사용을 고려하라
* 불필요한 생성을 피해야 한다.