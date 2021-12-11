# Item10. `equals()`는 일반 규약을 지켜 재정의 하라
## equals() 소개
자바를 공부하다보면 항상 맞닥뜨리는 일이 있습니다. 아마 코드만 보면 모두들 공감하실텐데요
```java
public class EqualTestSample{
    public static void main(String[] args) {
        Member member1 = new Member("Kim", 20);
        Member member2 = new Member("Park", 30);

        System.out.println(member1 == member2);         // false
        System.out.println(member1.equals(member2));     // true
    }
}
```
`==`은 **JVM Stack 영역에서 가지고 있는 값들을 비교하는 연산자**였습니다. (기본타입은 리터럴, 참조값은 참조 주소값을 비교) 
이를 어렵게 말해서 **동일성**이라고 말했었죠. `equals()`도 사실 코드를 까보면 비슷합니다.
```java
public class Object {
    public boolean equals(Object obj) {
        return (this == obj);
    }
}
```
결국 `Object`와 비교하는 파라미터가 같은 값을 참조하는지를 보여주는 것이죠. 하지만 여기서 넘어가시면 안됩니다. 
중요한것은 따로있습니다. 바로 **오버라이드가 가능하다**는 것 입니다! 이 방법을 적극적으로 이용한것이 바로 `String.equals()` 입니다.
```java
public final class String{
    public boolean equals(Object anObject) {
        // 일반적인 Object.equals()와 동일
        if (this == anObject) {
            return true;
        }
        // 중요한건 여기 오버라이드한 부분
        return (anObject instanceof String aString)
                && (!COMPACT_STRINGS || this.coder == aString.coder)
                && StringLatin1.equals(value, aString.value);
    }
}
```
* coder : 바이트를 인코딩하는데 사용되는 식별자.

결국 `String`은 **문자를 바이트로 인코딩해서 비교를 한 결과를 반환**하는 것입니다. 이처럼 완전히 동일하지 않아도 가지고 있는
내부 값이 같다면, 같다고 쳐주는 것을 **동등성**이라고 합니다. 이 개념을 다른 객체들에게도 적용할 수 있게 만드는 것이 바로 
`equals()`의 오버라이딩 기능입니다!


## `equals()` 재정의 기준
### 1. 각 인스턴스가 본질적으로 고유할 때.
객체가 값을 따로 가지는 것이 아니라, 동작만을 가지고 있을때(`Stateless`) 그의 인스턴스들이 여기에 해당합니다. 
앞서 말했던 싱글턴도 여기에 속하고, 가장 대표적인 예시가 바로 `Thread`이겠네요!

### 2. 인스턴스의 `논리적 동치성`을 검사할 일이 없을 때.
앞에서 설명했다시피 동일성을 증명하고 이를 기준으로 같다고 말할 수 있다면 재정의를 하지 않아도 됩니다. 이를 어렵게 말하면
**`논리적 동치성`을 검사할 일이 없는 경우**라고 하는데 이 상황에서는 오버라이드를 하지 않아도 됩니다.

### 3. 상위 클래스에서 재정의한 equals()가 하위 클래스에도 딱 들어 맞는 경우.
말 그대로입니다! 상위 클래스와 하위 클래스가 **완전한 `is-a` 관계**일때는 **equals()** 역시 딱 들어맞기 떄문에
따로 오버라이드 작업을 하지 않아도 됩니다. 필요하면 상위 클래스의 `equals()`를 가져다 사용하면 되니깐요!

### 4. 클래스가 private, package-private 이고 equals()를 호출할 일이 없는 경우.
너무 당연한 소리지만, 호출할 일이 없으면 애초에 만들 이유도 없습니다. 만약 다른 사용자가 `equals()`를 호출하는 것마저 막고싶다면
`equals()`를 오버라이드해 `AssertionError()`를 던지도록 하는 것도 방법입니다.

### 그럼 언제 재정의하란거야?
사실 윗 케이스들을 제외하면 재정의를 해주시는것이 맞습니다! 조금 구체적으로 얘기하자면 
바로 객체 식별성(주소값 비교)가 아닌 **논리적 동치성을 확인해야 할 경우**입니다.  
쉽게 말해서 **객체가 가지고 있는 내부 정보가 같으면 같은 인스턴스임을 증명하고 싶은 경우**에 오버라이드를 하시면 됩니다.


## 재정의 규약
equals 메서드는 동치관계를 구현하며 다음 조건들을 만족하라고 명세에 기술되어 있습니다.
### 1. 반사성 : null이 아닌 모든 참조 값 x에 대해 x.equals(x)는 참이다.

### 2. 대칭성 : null이 아닌 모든 참조 값 x, y에 대해 x.equals(y)가 참이면, y.equals(x)도 참이다.
딱 한마디로 축약하자면, **교환법칙을 만족**해야 합니다. 

### 3. 추이성 : null이 아닌 모든 참조 값 x, y, z에 대해 x.equals(y) && y.equals(z)가 참이면 x.equals(z)도 참이다.
형태를 보아하니 초등학교때 배운 **삼단논법**과 비슷합니다!

### 4. 일관성 : null이 아닌 모든 참조 값 x, y에 대해 x.equals(y)를 반복해서 호출해도 결과는 항상 같아야 한다.
이 역시 한마디로 축약할 수 있습니다. **멱등성을 만족해야 한다.**

### 5. null-아님 : null이 아닌 모든 참조 값 x에 대해 x.equals(null)은 거짓이다.


## 구현 방법
구현 순서는 다음과 같습니다. (Member.class 참고)
1. `==` 연산자를 통해 입력이 자기 자신의 참조인지 확인.
```java
@Override
public boolean equals(Object obj){
    if(obj==this){
        return true;
    }
}
```
2. instanceof 연산자를 통해 입력이 올바른 타입인지 확인.
```java
@Override
public boolean equals(Object obj){
    if(obj==this){
        return true;
    }
    if(!(obj instanceof Member)){
        return false;
    }
}
```
3. 입력을 올바른 타입으로 형변환 `Member m = (Member) obj;`

4. 핵심 필드들이 모두 일치하는지 하나씩 확인
     - 이때 float, double을 제외한 기본 타입 필드는 `==`을 이용
     - 이 두 타입은 각자의 정적 메서드인 compare() 사용
       - Float.NaN, -0.0f, 부동소수 값 등을 다뤄야 하기 때문!
```java
@Override
public boolean equals(Object obj) {
    if (obj == this) {
        return true;
    }
    if (!(obj instanceof Member)) {
        return false;
    }
    Member m = (Member) obj;
    return m.name == name && m.age == age;
}
```
5. 최적화를 고민하라.
   - **가장 불일치 가능성이 필드를 먼저 비교**를 해 `short-circuit`를 노리자!
   - 동기화용 락 필드 같이 **논리적 상태와 관련없는 필드는 비교하면 안된다!**

6. 다시 한번 코드를 봐라 => 대칭적인가, 추이성이 있는가, 일관적인가

7. equals() 재정의를 완료했다면 hashCode()도 재정의 하라.
   * 다음 Item에서 다루겠지만 `HashSet`, `HashMap`과 같이 `hashCode`를 비교하는 곳에서 불일치가 발생할 수 있습니다!


## 결론
* `equals()`를 재정의하고자 한다면 욕심을 부려선 안됩니다. 어렵게 느껴지면 아예 불가능한 구조인 경우가 대부분이기 떄문에
만약 상속 관계에서 `equals()`를 적용하고 싶다면 포기하는편이 빠릅니다.

* `equals()` 구현에 부담을 느낀다면 외부 라이브러리를 적극적으로 사용하는것도 한 방법입니다.
대표적인 예시가 바로 구글에서 제공하는 `AutoValue` 툴이 있습니다.

* 다짜고짜 `equals()`를 오버라이드 하면 예상치 못한 곳에서 오류가 발생할 수 있기 때문에 정말 오버라이드가 필요하다고 느끼는 순간에
`equals()` 사용을 권장합니다.

