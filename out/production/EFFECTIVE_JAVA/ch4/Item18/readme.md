# Item18. 상속보다는 컴포지션을 사용하라.
코드를 재사용할 수 있는 강력한 수단 중 하나입니다. 하지만 코드 재사용을 위해서 상속을 사용하는건 굉장히 나쁜 선택일지 모릅니다.
왜냐하면 상속은 **캡슐화를 깨트리기 때문**입니다.

## 캡슐화를 깨트리는 상속
캡슐화가 깨진다는 말은 상위 클래스를 수정하면 이를 상속받은 하위 클래스들에게 모두 영향이 간다는 소립니다.
특히 **패키지의 경계를 넘어 다른 패키지의 구체 클래스를 상속**하는 것은 매우 위험한 방법입니다.

### 예시:  Set과 InstrumentedSet
상속의 문제 중 하나는 메서드가 의도대로 동작하지 않을 수 있다 것입니다.
```java
public class InstrumentHashSet<E> extends HashSet {
    public static void main(String[] args) {
        InstrumentHashSet<String> s = new InstrumentHashSet<>();
        s.addAll(List.of("A", "AAA", "B"));
        System.out.println(s.getAddCount());
    }
  
    private int addCount = 0;

    public InstrumentHashSet() {}

    public InstrumentHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public boolean add(Object o) {
        addCount++;
        return super.add(o);
    }

    @Override
    public boolean addAll(Collection c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount(){
        return addCount;
    }
}
```
해당 코드에서는 `Set`에 추가를 할 때마다 `addCount`를 1씩 올리기를 원하고 있습니다. 하지만 결과는 아쉽게도 6이 나옵니다.
이유는 바로 `HashSet.addAll()`은 `add()`를 재귀적으로 호출하기 때문입니다!
```java
public boolean addAll(Collection<? extends E> c) {
    boolean modified = false;
    for (E e : c)
        if (add(e))     // add 호출
            modified = true;
    return modified;
}
```
이를 해결하는 방법은 `InstrumentHashSet.addAll()`을 제거하거나, 오버라이딩하는 방법이 있습니다. 하지만 이들은 완벽한 해결책이 되지 않습니다.
이 책의 저자 죠슈아 블로치는 `클래스가 상속되기를 원한다면 상속을 위해 클래스를 설계하고 문서화해야 하며, 그렇지 않는 경우에는
상속을 금지시켜야 한다`라고 말합니다. 그의 의견에 따르면 이 케이스에서는 상속을 사용하면 안됩니다. 그럼 대안은 없을까요? 있습니다! 바로 **합성**입니다!

## 합성(Composition)
합성은 상속과 함께 객체지향 프로그래밍에서 가장 널리 사용되는 코드 재사용 기법입니다. **상속이 부모 클래스와 자식 클래스를 연결해 부모 클래스의 코드를 재사용**
한다면, **합성은 전체를 표현하는 객체가 부분을 표현하는 객체를 포함해 부분 객체의 코드를 재사용**하는 방법입니다. 그래서 보통 상속을 `is-a`관계라 하고
합성을 `has-a` 라고 합니다.  
<br></br>
상속의 문제점을 다시 한번 언급하자면, 부모 클래스의 내부 구현에 대해 상세하게 알아야 하고 그로 인해 부모와 자식 클래스간의 결합도가 너무 높아진다는 것이었습니다.
그렇기 때문에 상속은 간단한 방법일지는 몰라도, 우아한 방법은 아니었죠.  
합성은 다릅니다. 구현에 의존할 필요가 없습니다! 합성은 **내부에 포함되는 객체의 구현이 아닌 퍼블릭 인터페이스에 의존**하기 때문에 내부 구현이 변경되더라도
영향을 최소화할 수 있습니다!

### 구현 방법
* 자바의 인터페이스를 통해 기본적인 퍼블릭 인터페이스는 그대로 받으며, 필요한 부분만 수정.
* 이때 필요한 부분은 기존 클래스의 인스턴스를 필드로 선언해 이 기능을 가져와 수정을 하면 된다.
* 새 클래스의 인스턴스 메서드들은 기존 클래스의 대응하는 메서드를 호출 해 그 결과를 반환한다. 이 방식을 **포워딩(전달)** 이라고 한다.
```java
public class InstrumentedHashSetByComposition<E> implements Set<E> {
    private Set<E> set;
    private int addCount = 0;
  
    public InstrumentedHashSetByComposition(Set<E> set) {
        this.set = set;
    }
  
    @Override
    public boolean add(E e) {
        addCount++;
        return set.add(e);
    }
  
    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return set.addAll(c);
    }

    public int getAddCount () {
        return addCount;
    }
    // 기본 Set의 퍼블릭 인터페이스 가져오기
    @Override
    public int size () {
        return set.size();
    }
}
```
이 방법을 통해 Set 인터페이스를 실체화하면서, 내부에 HashSet의 인스턴스를 합성하면 HashSet에 대한 구현 결합도는 제거하면서, 퍼블릭 인터페이스는
그대로 유지할 수 있게 됩니다!

### 테스트
```java
public class CompositionTestSample {
    public static void main(String[] args) {
        InstrumentedHashSetByComposition<String> set = new InstrumentedHashSetByComposition<>(new HashSet<>());
        set.addAll(List.of("이 패턴은", "데코레이터", "패턴이라고도", "한다"));
        System.out.println("결과는 4 입니다 = " + set.getAddCount());
    }
}
```
합성의 재밋는 점은 **의존관계가 컴파일 단계(or 코드 작성 단계)가 아닌 런타임 단계에서 생성된다는 것 입니다.**
덕분에 캡슐화를 깨트리지 않으면서 엄청난 유연성을 확보할 수 있게 됩니다.


### 주의점
* 래퍼 클래스가 콜백 프레임워크와는 어울리지 않는다는 것을 주의해라
  * 콜백에서는 자기 자신의 참조를 다른 객체에 넘겨 다음 호출때 사용한다.
  * 내부 객체는 자신을 감싸고 있는 래퍼의 존재를 모르기 때문에 자신(this) 참조를 넘기고 콜백 때 래퍼가아닌 내부 객체를 호출하게 된다.
  * 이러한 문제를 SELF 문제라고 한다.

## 결론
* 상속은 강력한 기능을 제공하나, 그만큼 큰 리스크를 짊어지고 있다.
* 그래서 상속은 반드시 하위 클래스가 상위 클래스의 **진짜 하위 타입인 상황**에서만 쓰여야 한다. (완전한 `is-a` 관계일때만 사용)
* 추가적으로 상속은 코드 재사용 목적으로 사용하는 것이 아닌, **타입 계층을 구현하는 목적으로 사용**되어야 한다.
* 이 경우를 제외하면 상속보다는 합성을 사용하는 것이 압도적으로 유리하다.