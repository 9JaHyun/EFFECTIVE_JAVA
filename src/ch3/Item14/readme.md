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
g