# Item1. 생성자 대신 정적 팩터리 메서드를 고려하라
* 가장 기본적으로 객체를 생성하는 방법은 생성자를 사용하는 방법입니다.
* 해당 문서에서는 정적 팩터리 메서드를 활용하는 것이 생성자를 사용하는 것 보다 좋다고 하는데 이유를 알아봅시다.

# 장점
## 1. 이름을 가질 수 있다.
* 생성자의 이름은 `ClassName()`로 항상 동일합니다. 
* 그렇기 때문에 정확한 의도를 전달할 수 없을 가능성이 생기게 됩니다.
* 하지만, 정적 팩터리 메서드를 사용한다면 얘기가 달라지게 됩니다.

```java
public class Rectangle {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Rectangle createRectangle(int width, int height) {
        return new Rectangle(width, height);
    }

    public static Rectangle createSquare(int width) {
        return new Rectangle(width, width);
    }

    public static void main(String[] args) {
        Rectangle rectangle = Rectangle.createRectangle(10, 5);
        Rectangle square = Rectangle.createSquare(10);
    }
}
```
* 해당 코드와 같이 명시적인 이름을 통해 반환될 객체의 특성을 쉽게 묘사할 수 있습니다.
  * `createRectangle()` : 직사각형 생성
  * `createSquare()` : 정사각형 생성

## 2. 호출될 때마다 인스턴스를 새로 생성하지 않게 만들 수 있다.
* 가장 대표적으로 싱글톤만 떠올려도 이 장점을 쉽게 납득하실 수 있을겁니다.
```java
public class Singleton {
    private static Singleton singleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            return new Singleton();
        } else return singleton;
    }
}
```
* 정적 팩토리 메서드를 통해 생성자를 호출하기 때문에 인스턴스를 계속해서 사용할 수 있습니다.
* 이런 방식으로 인스턴스의 생명주기를 통제할 수 있는데 이러한 클래스를 **인스턴스-통제 클래스**라 합니다.
* 이 개념이 확장이 되어 **플라이웨이트 패턴**과, **열거 타입**이 만들어지게 됩니다.

## 3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.
* 장점1과 혼합을 하게 된다면 강력한 기능을 선보일 수 있습니다.

```java
public class BirdV1 {
    void fly() {
        System.out.println("날다!");
    }
}

class doveV1 extends BirdV1 {
    @Override
    void fly() {
        System.out.println("비둘기가 날다!");
    }
}

class PenguinV1 extends BirdV1 {
    @Override
    void fly() {
        System.out.println("펭귄은 못난다...");
    }
}


```
* 해당 코드에서 doveV1과 PenguinV1을 실행하기 위해서는 항상 구현 객체를 생성해서 호출해야 합니다.
* 이는 클라이언트에게 구현 클래스를 공개하는 여지를 주게 됩니다.

```java
// 적용 이후
public class BirdV2 {
  public static BirdV2 createDove() {
    return new dove();
  }

  public static BirdV2 createPenguin() {
    return new Penguin();
  }

  void fly() {
    System.out.println("날다!");
  }

}

class dove extends BirdV2 {
  @Override
  void fly() {
    System.out.println("비둘기가 날다!");
  }
}

class Penguin extends BirdV2 {
  @Override
  void fly() {
    System.out.println("펭귄은 못난다...");
  }
}
```
* 이렇게 되면 구현 클래스의 호출을 숨길 수 있습니다.

```java
// 실행코드
public class BirdTestSample{

  public static void main(String[] args) {
      // 정적 팩토리 메서드 적용 전
      BirdV1 birdV1 = new BirdV1();
      BirdV1 dove = new doveV1();
      BirdV1 penguin = new PenguinV1();
      
      birdV1.fly();
      dove.fly();
      penguin.fly();

      // 정적 팩토리 메서드 적용 후
      BirdV2 bird = new BirdV2();
      BirdV2 dove = BirdV2.createDove();
      BirdV2 penguin = BirdV2.createPenguin();
  }
}
```
* 결과는 동일합니다!

* 이 장점을 채택한 대표적인 API가 바로 Collections들 입니다.

## 4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
* 생성자는 동일한 시그니처를 가지는 경우 생성이 불가능합니다.
* 하지만 정적 팩토리 메서드를 사용하게 된다면 이 문제를 극복할 수 있습니다.
* 다음 Account 코드를 봅시다. 잔액(Balance)가 양수라면 일반적인 통장을 음수로 만들면 마이너스 통장을 만든다고 해봅시다.
```java
public class Account {
    private String owner;
    private int balance;

    public Account(String owner, int balance) {
        this.owner = owner;
        this.balance = balance;
    }
    
    public static Account createAccount(String owner, int balance) {
        if(balance > 0) return new NormalAccount(owner, balance);
        return new MinusAccount(owner, balance);
    }

    public void showStatus() {
        showInfo();
        System.out.printf("고객명: %s, 잔액: %d", owner, balance);
    }

    public void showInfo() { }
}

class NormalAccount extends Account{
    public NormalAccount(String owner, int balance) {
        super(owner, balance);
    }
    
    public void showInfo() {
        System.out.println("일반 통장");
    }
}

class MinusAccount extends Account{
    public MinusAccount(String owner, int balance) {
        super(owner, balance);
    }
    
    public void showInfo() {
        System.out.println("마이너스 통장");
    }
}
```
* 이 코드의 정적 팩토리 메서드 `createAccount()`가 리턴하는 결과는 총 두가지 입니다.
  * Balance >= 0 : NormalAccount 반환
  * Balance < 0 :MinusAccount 반환

* 이렇게 파라미터에 따라 반환되는 클래스가 달라지게 됩니다!
```java
public static void main(String[] args) {
    Account account1 = Account.createAccount("테스트1", 15000);
    Account account2 = Account.createAccount("테스트2", -15000);
    account1.showStatus();
    account2.showStatus();
    }
```
* 결과
[!accountTest](./img/accountTest.png)

## 5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
* 생성자의 경우에도 완전한 객체를 생성하지 않고 `setter` 메서드를 활용해 입력할 수 있지만 해당 방식은 많은 위험을 초래할 수 있습니다.
  * 항상 메서드 실행에 중요한 파라미터를 숙지해야 한다.
* 정적 팩토리 메서드의 경우에는 이 메서드가 종료될 때 까지만 클래스가 완성되면 되기 때문에, 중간 과정에서 클래스가 존재하지 않거나, 완전하지 않아도 상괸 없습니다.
* 가장 대표적인 예시가 JDBC 프레임워크 입니다.

# 단점
* 이번에는 단점을 알아봅시다.

## 1. 상속을 하려면 public, protected 생성자가 필요해 정적 팩터리 메서드만 제공할 수 없다.
* 모든 생성자에는 `super()`가 숨어 있습니다. (객체는 최상위 클래스 `Object`를 상속받기 때문)
* 이를 접근하려면 당연히 상위 클래스의 생성자에 접근할 수 있어야 합니다.
* 하지만 `private`라면 접근할 수 없는 것이 당연합니다. 이를 위해 접근 제어자를 `public`, `protect`로 설정해야 합니다.
* 하지만 현재 객체지향의 트렌드가 **상속보다는 합성**을 추구하고 있기 때문에 이 단점은 **합성**으로 유도하는 장점? 처럼 보이기도 합니다.

## 2. 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.
* 생성자는 모든 프로그래머들이 알고 있는 객체 생성 방식입니다. `new ClassName(Paremter)`
* 하지만, 정적 팩터리 메서드의 경우 개발자들의 의도에 따라 이름이 달라지기 때문에, 이 코드를 처음 접하게 되는 개발자라면 혼동이 올 수 있습니다.
* 이 때문에 정적 팩터리 메서드의 명명 방식에 대한 [컨벤션](https://stackoverflow.com/questions/3368830/how-to-name-factory-like-methods)이 존재합니다.
