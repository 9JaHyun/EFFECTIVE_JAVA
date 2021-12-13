# Item42. 익명클래스보다는 람다를 사용하라
## 함수형 인터페이스
자바에서 함수 타입을 표현하기란 대단히 어려웠습니다. 대부분 추상 메서드를 하나만 담은 인터페이스를 사용했었죠.
이런 인터페이스를 **함수형 인터페이스**라 하며, 인스턴스를 **함수 객체**라고 불렀습니다. 이를 통해 특정 함수나 동작을 나타냈습니다.  
해당 동작을 구현하는 가장 일반적인 방법은 **익명 클래스**를 사용하는 방법이었습니다.
```java
class ImplementsFunctionalInterface{
    public static void main(String[] args) {
        Collections.sort(words, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length, s2.length());
            }
        });
    }
}
```
나쁘지 않은 방법이나, 코드가 점점 길어지기 시작하면서 다른 방법이 필요하게 되었습니다. 그렇게 시간이 지나 JAVA8에서는
이 함수형 인터페이스들의 인스턴스를 **람다식**를 사용해 만들 수 있게 만들었습니다.

## 람다식
함수 시그니처를 통해 직관적으로 만들어진 람다식을 통해 코드를 정리하고 동작을 명확하게 드러낼 수 있게 되었습니다!
```java
class ImplementsFunctionalInterface{
    public static void main(String[] args) {
        Collections.sort(words, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });

        Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
    }
}
```
재밋는 점은 s1, s2을 사용할 때 타입을 따로 기입을 하지 않았다는 것입니다. 이는 자바의 타입추론 기능 덕분인데, 이를 이해해기가 참 어렵습니다.
그렇기 때문에 **타입을 명시해야 코드가 더 명확할 때만 제외하고는, 람다의모든 매개변수 타입은 생략하는 것이 좋습니다.**
그 후 컴파일러가 타입을 알 수 없다라는 오류를 냈을때만 타입을 명시해주면 됩니다.
  
예시를 하나 더 들어봅시다.
```java
public enum Operation {
    PLUS("+") { 
        public double apply(double x, double y) { return x + y; }
    },
    MINUS("-") {
        public double apply(double x, double y) { return x + y; }
    },
    TIMES("*") {
        public double apply(double x, double y) { return x + y; }
    },
    DIVIDE("/") {
        public double apply(double x, double y) { return x + y; }
    };

    private final String symbol;
    
    Operation(String symbol) {
        this.symbol = symbol;
    }
    
    @Override
    public String toString() {return symbol;}

    public abstract double apply(double x, double y);
}
```
해당 코드는 enum 요소에 따라 다른 결과를 반환하는 했습니다. 이를 람다로 구현하면..
```java
public enum OperationLambda {
    PLUS("+", (x, y) -> x + y),
    MINUS("-", (x, y) -> x - y),
    TIMES("*", (x, y) -> x * y),
    DIVIDE("/", (x, y) -> x / y);

    private final String symbol;
    // double 연산에 관한 함수형 인터페이스
    private final DoubleBinaryOperator op;

    OperationLambda(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public double apply(double x, double y) {
        return op.applyAsDouble(x, y);
    }
}
```
이렇게 함수형 인터페이스를 필드로 두고 활용하면 람다식을 통해 더욱 명확한 함수를 짤 수 있습니다.

## 생성자 레퍼런스
람다식에서 한술 더 뜨는 기능이 있습니다. 바로 생성자 참조(생성자 레퍼런스)라는 기능입니다.
```java
class ImplementsFunctionalInterface{
    public static void main(String[] args) {
        Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        Collections.sort(words, comparingInt(String::length));
        words.sort(comparingInt(String::length));
    }
}
```
`::`라는 기호를 통해 유틸리티 메서드를 따로 호출하지 않고도 사용할 수 있습니다.

## 주의사항
### 람다는 자신을 참조할 수 없다.
이것이 익명 클래스와의 가장 큰 차이점일텐데 람다는 **자신을 참조할 수 없습니다. (This.참조 불가)**
**람다에서의 this 키위드는 자신을 구현하는 바깥 인스턴스를 가르키기 때문에 만약 함수 객체가 자신을 참조해야 한다면 반드시 익명 클래스를 사용해야 합니다.** 
따라서 **람다를 직렬화하는일은 극히 삼가야 합니다.** **직렬화해야만 하는 함수 객체가 있다면 private 중첩 클래스의 인스턴스를 활용**합시다.