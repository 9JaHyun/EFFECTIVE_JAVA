# Item29. 이왕이면 제네릭 타입으로 만들라
JDK에서 제공되는 제네릭을 사용하는 건 쉽지만, 제네릭 타입을 직접 만드는건 어렵다. 그래도 배워두면 그만한 가치를 한다.
이전에 다루었던 `Stack.class`를 Array에서 List로 마이그레이션을 시도해보자

## Stack 마이그레이션
```java
public class ArrayStack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public ArrayStack(Object[] elements) {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if(size == 0) throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null; // 참조해제
        return null;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
```
### 클래스 선언에 타입 매개변수 추가
```java
public class ArrayStack<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public ArrayStack(E[] elements) {
        this.elements = new E[DEFAULT_INITIAL_CAPACITY];    // 실체화 불가 타입이므로 배열을 만들 수 없다.
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if(size == 0) throw new EmptyStackException();
        E result = elements[--size];
        elements[size] = null; // 참조해제
        return null;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
```
문제는 `E`는 실체화 불가 타입이기 때문에 배열으로 만들 수 없다는 것 입니다. 해결책은 두가지 입니다.
#### 1) 제네릭 배열 생성을 금지하는 제약을 대놓고 우회 (형변환)
```java
public class ArrayStack{
    private E[] elements;
    public ArrayStack(E[]elements){
            this.elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY]; // 대놓고 형변환
    }
}
```
추가적으로 E[]로 캐스팅했기 때문에 **실체화가 불가능한 E[]는 코드레벨에서 검증할 방법이 없습니다.** 그래서 개발자가 검증을 확신시켜줘야 합니다.
  * `private E[] elements;`이기 때문에 클라이언트로 반환되거나, 다른 메서드에 전달될 일이 없다 => 안전하다.
  * `@SuppressWarnings("unchecked")` 어노테이션으로 경고를 숨기기
  
```java
public class ArrayStack{
    @SuppressWarnings("unchecked")
    public ArrayStack(E[]elements){
            this.elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY]; // 대놓고 형변환
    }
}
```

#### 2) elements 필드의 타입을 E[]에서 Object[]로 변경
```java
public class ArrayStack{
    private Object[] elements;
    public ArrayStack(E[]elements){
            this.elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY]; // 대놓고 형변환
    }

    public E pop() {
        if(size == 0) throw new EmptyStackException();
        @SuppressWarnings("unchecked")
        E result = (E) elements[--size];
        elements[size] = null;
        return null;
    }
}
```
이 또한 실체화 불가 타입이기 때문에 체크 후 `@SuppressWarnings("unchecked")`으로 확인하면 됩니다.
<br></br>
첫 번째 방식은 형변환을 배열 생성 시 단 한 번만 해주면 디지만, 두 번째 방식은 원소를 읽을 때마다 해줘야 하기 때문에
보통 첫 번째 방식을 선호하는 편입니다. 하지만 E가 Object가 아닌 한 **배열의 런타임 타입이 컴파일타임 타입과 달라 힙 오염을 일으킬 수 있습니다.**

## 정리
* 클라이어트에서 직접 형변환하는 것 보다 제네릭 타입이 더 안전하다.
* 그렇기 때문에 새로운 타입을 설계할 때는 형변환 없이도 사용할 수 있도록 해야 한다.
  * 이를 달성하기 위해서는 제네릭 타입으로 만들어야 한다.