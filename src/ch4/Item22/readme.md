# Item22. 인터페이스는 타입을 정의하는 용도로만 사용하라
* 인터페이스는 자산을 구현한 클래스의 인스턴스를 참조할 수 있는 타입 역할을 한다.
    * 클래스가 어떤 인터페이스를 구현한다는 것은, 자신의 인스턴스로 무엇을 할 수 있는지를 클라이언트에게 얘기해주는 것!
    * 인터페이스는 오직 이 용도로만 사용되어야 한다!
* 이의 가장 큰 반례가 바로 상수 인터페이스다.
```java
public interface PhysicalConstants {
  static final double AVOGADROS_NUMBER = 6.022_140_857e23;
  static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
  static final double ELECTRON_MASS = 9.109_383_56e-31;
  
}
```
* 상수 인터페이스는 인터페이스를 정말 잘못 사용한 예다.
  * 먼저 상수는 외부 인터페이스가 아닌 내부 구현에 속한다. 이는 내부 구현 API를 그대로 노출하는 셈이 된다.
  * 두번째로는 클라이언트 코드가 내부 구현에 해당하는 이 상수들에 종속될 수 있다.
    * 그래서 더이상 사용하지 않는 상수여도, 바이너르 호환성을 위해 여전히 상수 인터페이스를 구현하고 있어야 할 수도 있다.

### 만약 상수를 공개할 목적이라면 더 합당한 선택지들이 있다.
* 특정 클래스나 인터페이스와 강하게 연관된 상수라면 그 클래스나 인터페이스 자체에 추가하는 방법.
  * 예시 : 모든 숫자 기본 타입의 박싱 클래스 (Integer, Double에 선언된 MAX_VALUE, MIN_VALUE)
* 열거타입(enum)이 더 적합하면 이를 사용해도 된다.
* 앞의 두 방법에 해당하지 않는다면 인스턴스화 할 수 없는 유틸리티 클래스에 담아 공개하자. (private 생성자)
```java
public class PhysicalConstants {
    private PhysicalConstants() { };
    
    public static final double AVOGADROS_NUMBER = 6.022_140_857e23;
    public static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
    public static final double ELECTRON_MASS = 9.109_383_56e-31;
}
```
* 만약 이렇게 유틸리티 클래스에 정의된 상수를 클라이언트에서 사용하려면 클래스 이름까지 함께 명시해야 한다.
  * 예시 : `PhysicalConstants.BOLTZMANN_CONSTANT` 
* 자주 쓰이는 상수라면 정적 임포트를 통해 클래스 명을 생략할 수 있다.