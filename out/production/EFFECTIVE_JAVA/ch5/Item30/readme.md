# Item30. 이왕이면 제네릭 메서드로 만들라
클래스와 마찬가지로 메서드 역시 제네릭으로 만들 수 있습니다.

## 제네릭 메서드 작성법
일단 문제가 있는 샘플부터 가져와 봅시다. 다음은 두 집합의 합집합을 반환하는 메서드입니다.

```java
public class sample {
    public static Set union(Set s1, Set s2) {
        Set result = new HashSet(s1);
        result.addAll(s2);
        return result;
    }
}
```
컴파일은 가능하나 타입 안전하지 않기 때문에 경고가 발생합니다.

### 타입 안정화
이 목표를 달성하기 위해서는 메서드 선언에서의 세 집합(입력: 2, 출력: 1)의 원소 타입을 타입 매개변수로 명시하고, 메서드 안에서도 이 타입
매개변수만 사용하게 수정하면 됩니다. 이를 선언하는 위치는 반환 타입 앞에 설정하시면 됩니다! (정확하게는 제한자와 타입 사이)

```java
public class sample {
    public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<E>(s1);
        result.addAll(s2);
        return result;
    }
}
```

### 불변객체를 여러 타입으로 활용할 수 있게 만들때
```java
private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;

@SuppressWarnings("unchecked")
public static <T> UnaryOperator<T> identityFunction() {
    return (UnaryOperator<T>) IDENTITY_FN;
}
```