# Item7. 다 쓴 객체 참조를 해제하라
자바는 C와 C++처럼 개발자가 직접 메모리를 관리하는 것이 아닌 JVM의 GC를 통해 메모리를 관리합니다. 덕분에 많은 개발자들이 편안함을 느낄 수 있게 되었죠
하지만 이게 메모리 관리에 신경을 쓰지 않아도 된다는 말은 아닙니다! 다음 예시를 봅시다.

## STACK
```java
public class BadStack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public BadStack(Object[] elements) {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if(size == 0) throw new EmptyStackException();
        return elements[--size];
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
```
문제점이 보이시나요? 바로 `pop()`에 있습니다. 실제 자료구조 Stack.pop()은 해당 Stack을 꺼내면 해당 value를 없애는 방식입니다.
하지만 여기 구현된 코드에서는 꺼낸 value를 없애는 코드가 어디에도 없습니다!
```java
public Object pop() {
    if(size == 0) throw new EmptyStackException();
    // 꺼내기만 하고 없애는 곳은 아무데도 없다.
    return elements[--size];
}   
```
이렇게 된다면 얘기가 달라집니다.  
[stack1](./img/stack1.png)  
`pop()`을 통해 배열에서 접근할 수 없음에도 불구하고, 여전히 값이 살아있기 때문에 GC에서 이를 회수해가지 못하게 됩니다.
이 상황이 계속될수록 문제는 점점 악화됩니다. 결국에는 `OutOfMemoryError`가 발생하게 되겠죠! 이를 **메모리 누수**라고 합니다.

## STACK 해결
이 문제를 해결하기 위해서는 `pop()`에서 추가적인 참조 해제를 진행해야 합니다.
```java
public Object pop() {
    if(size == 0) throw new EmptyStackException();
    Object result = elements[--size];
    elements[size] = null; // 참조해제
    return null;
}   
```
이렇게 만들면 해당 참조변수를 사용하면 `NullPointException`이 발생하게 됩니다.

## 캐시와 메모리 반환
캐시를 사용할 떄도 메모리 누수 문제를 조심해야 한다. 객체 레퍼런스를 캐시에 넣어 놓고 캐시를 비우는 것을 잊기 쉽습니다.
여러가지 해결책이 있지만, 캐시의 키에 대한 레퍼런스가 캐시 밖에서 필요 없어지면 해당 엔트리를 캐시에서 자동으로 비워주는 `WeakHashMap`을 쓸 수 있습니다.
  
다른 방법으로는 특정 시간이 지나면 캐시값이 의미가 없어지는 경우 백그라운드 스레드를 사용(`ScheduledThreadPoolExecutor`)하거나, 
새로운 엔트리를 추가할 때 부가적인 작업으로 기존 캐시를 비우는 작업을 추가적으로 해주어야 합니다.
`LinkedHashMap` 클래스는 `removeEldestEntry`라는 메서드를 제공합니다.

## 콜백(리스너)
다음으로는 콜백입니다. 클라이어트 코드가 콜백을 등록할 수 있는 API를 만들고 빼는 방법을 제공하지 않는다면, 계속 콜백이 쌓이게 됩니다.
이것 역시 `WeakHashMap`을 사용해서 해결할 수 있습니다.


## 주의사항
이 방법을 알았다고 해서 사용한 모든 객체 참조들을 null로 처리하지 않아도 됩니다! 이 방법은 **아주 예외적인 케이스**로, 이를 제외하면 오히려 코드를 지저분하게 만들 수 있기 때문에
지양해야합니다. 그럼 이 방법을 제외하고 어떻게 해야 메모리 반환을 할 수 있을까요? 방법은 JAVA를 처음 공부할 때 변수 파트에서 배웠을 겁니다. **유효범위(Scope) 밖으로 밀어내면 됩니다.**  
메모리 반환에 대해 따로 신경쓰고 싶지 않다면 **항상 변수의 유효범위를 최소로 만들면 됩니다.** 그럼 GC가 자연스럽게 다 쓴 참조들을 처리할 것입니다.

# 결론
* JVM이 메모리를 관리해준다고 해서 메모리에 신경을 꺼도 된다는 것이 아니다!
* 메모리 누수를 주의하라 대표적인 예시가 콜백, 캐시다.
  * WeakHashMap을 적극적으로 활용하자.
* 누수는 철저한 코드 리뷰, `heap profiler` 같은 디버깅 도구를 사용해야 한다.
* 가장 중요한 것은 예방이다!