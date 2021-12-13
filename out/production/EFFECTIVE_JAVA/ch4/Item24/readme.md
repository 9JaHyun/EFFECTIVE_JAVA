# 멤버 클래스는 되도록 static으로 만들라
중첩 클래스란 다른 클래스 안에 정의된 클래스를 말합니다. **중첩 클래스는 자신을 감싼 바깥 클래스에서만 쓰여야 하며**, 
그 외 다른 쓰임새가 있다면 반드시 내부 클래스가 아닌 또다른 클래스로 만들어야 합니다.

# 중첩 클래스 종류
중첩 클래스 종류는 다음과 같습니다.
* 정적 멤버 클래스
* 비정적 멤버 클래스    
* 익명 클래스
* 지역 클래스

하나하나 살펴봅시다!

## 멤버 클래스
### 정적 멤버 클래스
다른 클래스 안에 선언되고, 바깥 클래스의 private 정적 멤버에도 접근할 수 있다는 점 말고는 일반 클래스와 동일합니다.
**단 접근하려는 필드가 static 해야만 접근이 가능합니다!** 메모리에 올라가는 스코프가 같아야 사용을 할 수 있겠죠?  
```java
public class StaticMemberClass {
  private String field1;
  private static String field2;

  void doSomething() {
    field1 = "hello";
    System.out.println("Do Something by OuterClass : " + field1);
  }

  static class InnerClass{
    void doSomething() {
      field2 = "GoodBye";
      // 단순 인스턴스 필드는 접근 불가
//    System.out.println("Do Something by InnerClass : " + field1);

      // 정적 필드는 접근 가능!
      System.out.println("Do Something by InnerClass : " + field2);
    }
  }
}
```

정적 멤버 클래스는 흔히 **바깥 클래스와 함께 쓰일 때만 유용한 public 도우미 클래스로 쓰인다.** 
만약 **중첩 클래스의 인스턴스가 바깥 인스턴스와 독립적으로 존재할 수 있으면 정적 멤버 클래스로 만들어야 합니다.**
```java
public class TestSample {
    public static void main(String[] args) {
        // Static Member Class의 경우 독립적으로 생성 가능.
        StaticMemberClass staticMemberClass = new StaticMemberClass();
        StaticMemberClass.InnerClass innerClass = new StaticMemberClass.InnerClass();
    
        staticMemberClass.doSomething();
        innerClass.doSomething();
    }
}
```

### 비정적 멤버 클래스
외적으로만 봤을때는 `static`이 있느냐 없느냐 차이이지만 내부적으로 봤을때 차이가 큽니다.
비정적 멤버 클래스의 인스턴스는 바깥 클래스의 인스턴스와 암묵적으로 연결된다. 그래서, 비정적 멤버 클래스의 인스턴스를 생성하거나, 메서드를 호출할 경우
`정규화된 this(ClassName.this)`를 사용해 바깥 인스턴스의 메서드를 호출할 수 있습니다.
```java
public class NonStaticMemberClass {
    private String field1;

    void doSomething() {
        field1 = "goodBye";
        System.out.println("Do Something by NonStaticMemberClass : " + field1);
    }

    // 일반적으로는 외부 클래스의 인스턴스 메서드에서 멤버 클래스 인스턴스를 생성해 호출하는 편.
    void runInnerClassDoSomething() {
        InnerClass innerClass = new InnerClass();
        innerClass.doSomething();
    }

    // 비정적 멤버 클래스
    class InnerClass{
        void doSomething() {
            field1 = "hello";
            System.out.println("Do Something by InnerClass : " + field1);
        }

        // 정규화된 This를 통해 바깥 클래스를 호출
        void runOuterClassMethod() {
            NonStaticMemberClass.this.doSomething();
        }
    }
}
```
비정적 멤버 클래스의 인스턴스를 생성하기 위해서는 반드시 외부 클래스의 인스턴스가 필요합니다. 이렇게 **비정적 멤버 클래스의 인스턴스와 외부 클래스의
인스턴스 사이의 관계는 멤버 클래스가 인스턴스화될 때 부터 확립**되며, 변경이 불가능합니다. 이 과정으로 인해 비정적 멤버 클래스의 인스턴스 안에
관계 정보가 만들어지기 때문에, 추가적인 메모리 공간과, 생성 시간이 더 들게 됩니다.  
그렇기 때문에 **외부 필드나, 메서드를 사용하지 않는다면 정적 멤버 클래스로 선언해 불필요한 참조를 맺지 않게 막아야 합니다.**
```java
public class TestSample {
    public static void main(String[] args) {
        NonStaticMemberClass nonStaticMemberClass = new NonStaticMemberClass();
//        NonStaticMemberClass.InnerClass innerClass1 = new NonStaticMemberClass().InnerClass();    독립적으로 생성 불가

        // 외부 클래스의 인스턴스 메서드에서 멤버 클래스를 호출하는 방법을 사용하지 않는다면 클라이언트 코드에서 이 방법을 통해 호출.
        NonStaticMemberClass.InnerClass innerClass1 = nonStaticMemberClass.new InnerClass();
        innerClass1.doSomething();
        innerClass1.runOuterClassMethod();
    }
}
```
보통 외부 클래스의 인스턴스 메서드에서 비정적 멤버 클래스의 생성자를 호출할때 자동적으로 만들어지지만, 드물게는 직접 클라이언트 코드에서
`outerClassInstance.new MemberClassName()`를 호출해 수동으로 만들기도 합니다. 

### 결론
* **멤버 클래스가 바깥 인스턴스에 접근할 일이 없다면 무조건 static을 붙여서 정젝 멈버 클래스를 만들자.**
  * 불필요한 외부 참조를 막기 위해서 정적 멤버 클래스로 선언하라.
  * 비정적 멤버 클래스를 사용하게 되면 외부 참조로 인해 멤버 클래스가 GC 대상이 되지 않는 이상 외부 클래스 역시 GC하지 못하기 때문에 메모리 누수가 생긴다.

## 익명 클래스
익명클래스는 보통 멤버클래스로 사용되는 것이 아닌, 파라미터로 사용이 되는 편입니다. 특징으로는 멤버 클래스들과 달리 쓰이는 시점에 
선언과 동시에 인스턴스가 만들어지기 때문에 코드의 어디서든 생성이 가능합니다. 그렇기 때문에 그래서 오직 비정적(None-static)인 문맥에서 
사용될 때만 바깥 클래스의 인스턴스를 참조할 수 있습니다.  
```java
public interface MyInterface {
    void doSomething();
    
    static void staticMethod() {
        System.out.println("Static Method");
    } 
}

public class TestSample {
    public static void main(String[] args) {
        // 익명 클래스
        MyInterface myInterface = new MyInterface() {
            @Override
            public void doSomething() {
                System.out.println("익명 클래스");
            }
        };
        myInterface.doSomething();
    }
}
```
<br>
추가적으로 다른 클래스에 비해서 제약이 많은 편 입니다.
* Static 문맥에서는 상수 이외의 정적멤버는 가질 수 없다.
* 선언된 위치에서만 인스턴스를 만들 수 있다.
* instanceof 검사나 **클래스의 이름이 필요한 작업은 당연히 수행하지 못한다.**
 
## 로컬(지역) 클래스
지역변수를 선언할 수 있는 곳이라면 실질적으로 어디서든 선언이 가능합니다. 스코프 역시 지역변수와 동일한 스코프를 가집니다.
말이 어려운데 지역 변수를 선언할 수 있는 곳은 결국 메서드 내부입니다. 그렇다는 건 **메서드 내부에서 정의한 클래스**를 말합니다. 
생소한 만큼 가장 드물게 사용되는 방식입니다. 보통 비동기 처리를 위해 스레드 객체를 생성할 때으로 많이 사용됩니다.
```java
public class LocalClass {
    private String field1;
    private String field2;

    public void doSomething() {
        String localField1 = "Hello";
        String localField2 = "LocalClass";
        class DoSomethingLocalClass {
            String plusString(String str1, String str2) {
                return str1 + " " + str2;
            }
        }
        DoSomethingLocalClass localClass = new DoSomethingLocalClass();
        String s = localClass.plusString(localField1, localField2);
        System.out.println(s);
    }
}
```
## 결론
* 메서드 밖에서 사용해야 하거나, 메서드 안에서 정의하기에 너무 길다면, **멤버 클래스**
  * 멤버 클래스의 인스턴스가 **바깥 인스턴스를 참조한다면 비정적**
  * 그렇지 않으면 정적 (자원을 최대한 아껴라!)
<br></br>
* 중첩 클래스가 한 메서드 안에서만 쓰이면서, 그 인스턴스를 생성하는 지점이 단 한곳이면 익명 or 로컬(지역) 클래스
  * **해당 타입을 쓰기에 적합한 클래스나 인터페이스가 이미 존재한다면 익명 클래스.**
  * 그렇지 않으면 지역 클래스.