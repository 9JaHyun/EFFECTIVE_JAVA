# Item23. 태그 달린 클래스보다는 클래스 계층구조를 활용하라
조금 추상적인 의미를 가지는 클래스라면 필드를 통해 여러 의미를 표현할 수 있으며, 그중 현재 표현하는 의미를 태그 필드 값으로 알려주는 클래스를 
본 적이 있을 겁니다. 대표적인 것이 바로 **enum 필드를 활용한 태그**입니다.
```java
public class Figure{
    enum Shape{RECTANGLE, CIRCLE};

    // 태그 필드
    final Shape shape;
  
    double height;
    double width;
    
    double radius;

    // Rectangle 생성자
    Figure(double height, double width) {
        shape = Shape.RECTANGLE;
        this.height = height;
        this.width = width;
    }

    // Circle 생성자
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }
}
```
이렇게 태그 달린 클래스들은 장황해 가독성이 나쁘고, 오류를 내기 쉬우며, 자원 소모도 큽니다.

## 문제점
이 추상적인 클래스를 구분하는 방법은 태그 필드를 활용하는 방법이다. 그러면 이 필드에 접근을 해야 하고, 추가적으로 필드에 따라 동작방식을 다르게
지정하는 방법도 수동으로 해주어야 한다.
```java
public double getArea{
    switch(shape){
        case RECTANGLE:
            return width * height;
        case CIRCLE:
            return PI * (radius * radius);
    }
}
```
이렇게 `Switch` 연산자를 통해 태그 필드를 먼저 파악한 후 동작 방식을 결정하고 있습니다. 덕분에 코드가 장황해지고, 필요없는 자원소모도 들게 됩니다.
결국 **태그가 달린 클래스는 클래스의 계층구조를 어설프게 흉내낸 아류**일 뿐입니다. (태그를 쓰느니 차라리 상속을 사용하는게 나을 정도니깐요!)

## 그러면..?
객체 지향에서는 클래스 계층구조를 활용하는 서브 타이핑 방법이 있습니다. (인터페이스 상속이라고 부르기도 합니다.)
```java
public interface Figure {
  double getArea();
}

public class Circle implements Figure {
    final double radius;
  
    Circle(double radius){
        this.radius = radius;
    }
  
    @Override
    public double getArea() {
        return Math.PI * (radius * radius);
    }
}

public class Rectangle implements Figure {
    final double radius;
  
    Circle(double radius){
        this.radius = radius;
    }
  
    @Override
    public double getArea() {
        return Math.PI * (radius * radius);
    }
}
```
이 방법을 통해 계층구조를 유연하게 만들 수 있습니다.

## 결론
* 태그 달린 클래스를 써야 하는 상황은 없다고 봐도 무방하다.
* 만약 태그 달린 클래스를 발견했다면 태그를 없애고 계층구조로 대체(리팩토링)하는 방법을 고안해라.