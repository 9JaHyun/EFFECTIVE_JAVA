# Item14. `Comparable`을 구현할지 고려하라
이번에는 `Object`의 유틸리티 메서드는 아니지만, 공통적으로 재정의는 해야 하는 `compareTo()`에 대해서 알아보겠습니다.

## `compareTo()`?
이 메서드는 `equals()`와 거의~ 비슷합니다. 단 한 가지만 다르죠. 바로 `순서`입니다! 
자바 명세에서도 다음과 같이 설명이 되어 있습니다.
```text
This interface imposes a total ordering on the objects of each class that implements it.
This ordering is referred to as the class's natural ordering, 
and the class's compareTo method is referred to as its natural comparison method.
```
* 객체의 전체 순서를 부과한다. (이를 자연 순서라고 합니다.)

이렇게 `compareTo()`는 단순 동치성만 비교해주는 `equals()`의 그 이상의 역할을 수행하고 있는 것이죠.
덕분에 이 기능을 구현한 객체들의 배열은 손쉽게 `sort()`를 사용하기만 하면 의도한대로 정렬이 가능합니다.
추가적으로 컬렉션 관리도 쉬워지게 됩니다. 대표적인 예시가 바로 `TreeSet`, `TreeMap`이 있겠네요!

## 구현 규약
이렇게 알파벳, 숫자, 등 순서가 명확한 클래스를 작성해야 한다면 반드시 `Comparable` 인터페이스를 구현해야 합니다.
이를 구현하기 위해서 자바는 다음 규약을 지켜야 한다고 합니다.
```text
이 객체가 주어진 객체보다 작으면 음의 정수를, 같으면 0울, 크면 양의 정수를 반환하라.
비교할 수 없는 타입의 객체가 주어지면 ClassCastException을 던진다.
```

### 1. `(x.compareTo(y)) == -(y.compareTo(x))`여야 한다.
당연한 소리입니다. 순서가 바뀌면 당연히 대소 관계에 대한 결과값도 정반대로 변하기 때문에 `-`가 붙어야 하죠!
추가적으로, `x.compareTo(y)`가 예외를 던진다면, `y.compareTo(x)` 역시 예외를 던져야한다는 사실을 잊지 맙시다!

### 2. 추이성을 보장해야 한다. `x.compareTo(y) > 0 && y.compareTo(z) > 0 `라면 `x.compareTo(z)`여야 한다.
이전 `equals()`에서도 설명했지만 삼단논법에 관한 내용입니다.

### 3. `x.compareTo(y) == 0` 이면, `x.compareTo(z) == y.compareTo(z)`다.
x와 y가 같다는 뜻이 되니 당연한 결과입니다.

### 4. (x.compareTo(y) == 0) == x.equals(y) 여야 한다. 이 조건이 필수는 아니나, 이 조건을 지키지 않는다면 그 사실을 명시해야 한다.
이 조건을 지키지 않아도 동작은 합니다. 하지만, `Collection`, `Set`, `Map`에서는 조금 엇박자(불일치)가 생길 수 있습니다.
왜냐하면 **정렬된 클래스들은 동치성 비교 시 `equals()`가 아닌 `compareTo() == 0`을 사용하기 때문**입니다!


## 구현 방법
`equals()`와 비슷합니다. 단 `compareTo()`에서는 관계 연산자 `<`와 `>`는 오류를 발생시킬 수 있기 때문에 권장하지 않습니다.
각 기본형 타입의 래퍼클래스의 `compare()`를 사용하시면 됩니다.  
가장 핵심적인 필드부터 비교해나가는 것을 잊지 마세요!
```java
public class Member{
    public int compareTo(Object o) {
        Member m = (Member) o;
        int result = name.compareTo(m.name);
        if (result == 0) {
            result = Integer.compare(age, m.age);
//            필요하면 계속 if 중첩...
        }
        return result;
    }
}
```
### JAVA8 이후...
자바 8에서는 `Comparetor` 인터페이스를 이용해 연쇄 방식으로 비교자를 생성할 수 있게 되었습니다. 사용성이 정말 강력한 기능이지만,
어느정도의 성능 저하는 각오를 해야 합니다.
```java
public class Member{
    private static final Comparator<Member> COMPARATOR =
            Comparator.comparing((Member m) -> m.name)
                    .thenComparingInt(m -> m.age);
//    필요하면 계속 thenComparing 중첩...
    
    public int compareToV2(Member member) {
        return COMPARATOR.compare(this, member);
    }
}
```

### 해시코드 비교
해시코드를 비교하는 방법도 있습니다. 하지만 이 방법은 피합시다.
```java
public class Member {
    static Comparator<Object> hashCodeOrder = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            return o1.hashCode() - o2.hashCode();
        }
    };
}
```
이렇게 단순 연산방법은 정수 오버플로를 발생시키거나, 부동소수점 계산 방식에 따라 오류를 낼 수 있습니다. 그래서 다음 방법을 사용해야 합니다.
```java
public class Member{
    static Comparator<Object> hashCodeOrderV2 = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            return Integer.compare(o1.hashCode(), o2.hashCode());
        }
    };
}
```
이렇게 래퍼 객체의 `compare`을 사용하는 방법이 있고 또는 앞서 설명한 `Comparator` 연쇄 비교자를 사용하면 됩니다.
```java
public class Member{
    static Comparator<Object> hashCodeOrderV3 = Comparator.
            comparingInt(Object::hashCode);
};
```

## 결론
* 순서를 고려해야 하는 값 클래스를 작성한다면 꼭 `Comparable` 인터페이스를 구현해야 한다.
* 인터페이스를 구현할 때 (`compareTo()`) 비교 연산자`<, >`를 사용해서는 안된다!
  * 래퍼 클래스가 제공하는 정적 `compare` 메서드 사용
  * Comparator 인터페이스가 제공하는 비교자 생성 메서드(연쇄 방식)을 사용하자!