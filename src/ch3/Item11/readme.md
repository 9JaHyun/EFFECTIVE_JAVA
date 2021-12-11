# `equals()`를 재정의하려거든 `hashCode()`도 재정의하라.
앞에서 잠깐 설명했지만, `hashCode`를 비교하는 `HashMap`이나 `HashSet`같은 컬렉션의 원소로 사용될 때 오류를 방지하기 위해 
`hashCode()` 역시 같이 재정의를 해주어야 합니다.  
이전장의 `Member` 클래스를 살짝 가져와 `hashCode()`의 재정의 필요성을 몸소 느껴봅시다!
```java
public class MemberHashCodeTestSample {
    public static void main(String[] args) {
        Member member1 = new Member("Kim", 20);
        Member member2 = new Member("Park", 30);
        Member member3 = new Member("Park", 30);
        
        HashMap<Member, Integer> hashMap = new HashMap<>();

        hashMap.put(member1, 1);
        hashMap.put(member2, 2);
        hashMap.put(member3, 3);    // member2와 member3가 같으니 덮어쓰겠지?

        System.out.println(hashMap.values());
    }
}
```
하지만 결과는 놀랍게도 `[3, 1, 2]`가 나오게 됩니다.
이렇게 되면 우리가 앞서 했던 `equals()` 오버라이딩 작업이 쓸모가 없어지게 됩니다. 
왜냐하면 [`HashMap`은 `hashCode`를 추가로 비교하기 때문입니다!](https://d2.naver.com/helloworld/831311)
그래서 `Member`에 추가적으로 `hashCode()` 역시 오버라이드 해 주어야 합니다.  
자바 명세에서도 이를 강조하고 있습니다.
```text
  Whenever it is invoked on the same object more than once during an execution of a Java application, 
the {@code hashCode} method must consistently return the same integer, provided no information
used in {@code equals} comparisons on the object is modified.
This integer need not remain consistent from one execution of an application to 
another execution of the same application.
- equals() 비교에 사용되는 정보가 변경되지 않았다면, 애플리케이션이 실행되는 동안 그 객체의 
  hashCode()메서드는 몇 번을 호출해도 항상 같은 값을 반환해야 한다. 단, 애플리케이션을 재실행 했다면 이 값이
  달라져도 상관없다.
        
  If two objects are equal according to the {@link equals(Object) equals} method, then calling the
{@code hashCode} method on each of the two objects must produce the same integer result.
- equals(Object)가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 똑같은 값을 반환해야 한다.

  It is not required that if two objects are unequal according to the {@link equals(Object) equals} 
method, then calling the {@code hashCode} method on each of the two objects must produce distinct integer results.
However, the programmer should be aware that producing distinct integer results for unequal objects 
may improve the performance of hash tables.
- equals(Object)가 두 객체를 다르다고 판단했더라도, 두 객체의 hashCode가 서로 다른 값을 반환할 필요는 없다.
단 다른 객체에 대해서는 다른 값을 반환해야 해시테이블의 성능이 좋아진다. (= 결국 다른 값을 반환하라는 소리)
```
## 구현 방법
그렇다면 이제 구현봅시다!  
### 1. int 변수 result를 선언한 후 값 c로 초기화한다. (이때 c는 순서2에서 계산한 값이다.)

### 2. 값 c 계산
객체의 핵심 필드들을 이용하여 값 c를 계산해야 합니다.
#### 1) 필드가 기본 타입 필드라면 Type.hashCode(필드)를 수행. (이때 Type은 기본 타입 박싱 클래스)
```java
public class Member {
    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public int hashCode(){
        int result;
        name.hashCode();        // String hashCode()
        Integer.hashCode(age);  // int hashCode()
    }
}
```
#### 2) 만약 참조타입 필드면서 이 클래스의 `equals()` 메서드가 이 필드의 `equals()`를 재귀적으로 호출해 비교한다면 `hashCode()` 역시 재귀적으로 호출한다.
만약 계산이 더 복잡해질 것 같으면, 이 필드의 표준형을 만들어 그 표준형의 hashCode()를 호출하면 됩니다.

```java
public class ColorPoint {
    private Color color;
    private Point point;

    ColorPoint(Color color, Point point) {
        this.color = color;
        this.point = point;
    }

    @Override
    public boolean equals(Object obj) {
        ..
        ColorPoint cp = (ColorPoint) obj;
        // equals()를 재귀적으로 호출하고 있다면?
        return color.equals(cp.color) && point.equals(cp.point);
    }

    @Override
    public int hashCode() {
        // hashCode 역시 재귀적으로 호출
        color.hashCode() + point.hashCode();
    }
}
```
#### 3) 필드가 배열이라면, 핵심 원소 각각을 별도 필드처럼 다룬다.
원소들을 각각 꺼내 1), 2) 단계로 진행하면 됩니다. 만약 핵심 원소가 하나도 없으면 단순 원소 0을 반환하면 됩니다.
만약 모든 원소가 핵심 원소라면 한번에 계산하는 `Arrays.hashCode()` 유틸리티 메서드를 제공합니다.

### 3. 2단계에서 계산한 모든 해시코드 c로 result를 갱신하라.
단 여기서 31을 곱해줘야 합니다. `result = 31 * result + c`  
이는 31이 홀수이면서 소수이기 때문입니다. (짝수면, 시프트연산으로 인해 정보의 변형이 생길 수 있음.)

### 최종 결과
```java
public class Member {
    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public int hashCode(){
        int result = name.hashCode();
        result = 31 * result + Integer.hashCode(age);
        return result;
    }
}
```

## 결론
* `equals()`를 재정의 했다면, `hashCode()`를 사용하는 컬렉션 구조를 위해 `hashCode()` 역시 재정의 하라.
* 간단하게는 `Object.hash(핵심 파라미터)`도 있다. 하지만 이는 오토박싱으로 인한 성능 저하를 일으킬 수 있다.
* `hashCode()`도 `equals()`와 같이 IDE나 AutoValue에서 재정의 기능을 제공하고 있다.