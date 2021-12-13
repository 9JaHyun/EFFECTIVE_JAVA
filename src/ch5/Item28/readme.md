# Item28. 배열보다는 리스트를 사용하라.
배열과 제네릭을 사용한 컬렉션 프레임워크에는 큰 차이가 있습니다. 하나 하나 살펴봅시다.

## 배열 VS 제네릭
### 공변
배열은 **공변**입니다. 즉 **Sub가 Super의 하위 타입이라면, Sub[]는 배열 Super[]의 하위 타입이 된다는 것** 입니다.
반면 제네릭은 불공변입니다. 즉 Sub가 Super의 하위 타입이래도 List<Sub>는 List<Super>의 하위 타입이 아닙니다!
제네릭이 너무 융퉁성 없는게 아닌가? 라고 생각할지 몰라도, 사실 배열이 비정상적인 것 입니다.
다음 메서드들을 살펴봅시다.
```java
public class Covariant {
    // 런타임에서 에러가 난다.
    void test1() {
        Object[] objectArray = new Long[1];
        objectArray[0] = "타입이 달라 넣을 수 없다.";
    }

    // 컴파일러에서 에러가 난다
    void test2() {
        List<Object> objectList = new ArrayList<Long>();    // 불공변으로 만들어지는 컴파일 에러
        objectList.add("타입이 달라 넣을 수 없다");
    }
}
```
`test1()`에서 배열은 공변이기 때문에 컴파일 단계에서 Long[]에 문자열을 입력해도 문제를 제기하지 않습니다. 하지만 배열을 봅시다.
`test2()`에서는 **불공변이기 때문에 List<Object>와 ArrayList<Long>은 서로 연관되는 타입이 아닙니다.** (Sub, Sup 관계 X) 그렇기 때문에
선언에서 부터 컴파일 에러가 발생하게 되는 것이죠!
이렇게 런타임에서 오류를 알아차리는 것 보다는, 컴파일 단계서 부터 오류를 내는 편이 개발에서나 디버그면에서나 유리합니다.

### 실체화
두번째로는 배열은 **실체화**가 된다는 것 입니다. 쉽게 말하자면 배열은 런타임에도 자신이 담기로 한 원소의 타입을 인지하고 확인합니다.
반면 제네릭은 런타임 시 소거(Erasure)가 된다고 했습니다. 덕분에 레거시 코드에 대한 호환성을 유지할 수 있게 된 것이죠.

***
이 두가지 차이점 때문에 배열과 제네릭은 어우러지지 못합니다. 추가적으로 런타임 시 타입 안전하지 않기 때문에 더욱 두 요소는 멀어지게 되었습니다.
```java
public class CantGenericArray {
    public static void main(String[] args) {
        List<String>[] stringLists = new List<String>[1];  // 허용된다 가정할때....
        List<Integer> intList = List.of(42);               // 원소가 하나인 List<Integer> 생성
        Object[] objects = stringLists;                    // (1)에서 생성한 List<String> 배열을 Object 배열에 할당. (공변이기 때문에 가능)
        objects[0] = intList;                              // 문제 없음..
        String s = stringLists[0].get(0);                  // get(0)의 결과가 42인데,,, 이는 Integer이지 String이 아니다! ClassCastException
    }
}
```
```java
public class Chooser {
    private final Object[] choiceArray;

    public Chooser(Collection choices) {
        choiceArray = choices.toArray();
    }

    // choose()를 호출할 때마다 반환되는 Object를 원하는 타입으로 형변환 해야 한다.
    public Object choose() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();    
        return choiceArray[rnd.nextInt(choiceArray.length)];
    }
}

// 제네릭으로 만들기 1
class ChooserV1<T> {
    private final T[] choiceArray;

    // 동작은 하나, 런타임에도 안전한지 보장은 못한다.
    public ChooserV1(Collection<T> choices) {
        choiceArray = (T[]) choices.toArray();
    }

    // choose()를 호출할 때마다 반환되는 Object를 원하는 타입으로 형변환 해야 한다.
    public Object choose() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        return choiceArray[rnd.nextInt(choiceArray.length)];
    }
}

// 제네릭으로 만들기 2 : 경고를 숨기는 것 보다는 리스트로 만들자
class ChooserV2<T> {
    private final List<T> choiceList;

    // 동작은 하나, 런타임에도 안전한지 보장은 못한다.
    public ChooserV2(Collection<T> choices) {
        choiceList = new ArrayList<>(choices);
    }

    // choose()를 호출할 때마다 반환되는 Object를 원하는 타입으로 형변환 해야 한다.
    public Object choose() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        return choiceList.get(rnd.nextInt(choiceList.size()));
    }
}
```
다음 과정들을 참고해 배열은 왠만하면 리스트로 만들자.

## 결론
* 배열과 제네릭은 공변과 실체화의 관계에서 나뉜다.
  * 배열 : 공변, 실체화        => 런타임에 안전, 컴파일에서 안전X
  * 제네릭 : 불공변, 타입 소거  => 컴파일에서 안전, 런타임에서 안전X
* 만약 배열에서 컴파일 오류가 나온다면 리스트로 변경해보자.