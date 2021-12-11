# Item3. Private 생성자나 열거 타입으로 싱글턴임을 보증하라.
Item1에서도 살펴보았듯이 정적 팩토리 메서드를 통해서 싱글턴을 쉽게 구현할 수 있음을 설명했었습니다.  
여기서 추가적으로 해주어야 할 작업이 있는데 바로 **생성자를 private하게 만드는 것**입니다!  

## 생성자를 private하게 만들어 싱글턴 보증

```java
public class BadSingleton {
    private static BadSingleton INSTANCE = getInstance();

    public BadSingleton() {
    }

    private BadSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BadSingleton();
        }
        return INSTANCE;
    }
}
```
해당 클래스는 생성자에 대한 아무런 은닉 조치를 취하지 않았습니다. 
물론 클래스명에 싱글턴임을 명시했기 때문에 new로 생성할 개발자는 없겠지만...(설마?) 제가 그 악역을 자처해보겠습니다.
<br></br>
```java
public class SingletonTestSample {
    public static void main(String[] args) {
        // 안되!
        BadSingleton badSingleton = new BadSingleton();

        System.out.println(badSingleton.equals(BadSingleton.getInstance()));
    }
}
```
이렇게 눈물나는 상황이 벌어지게 됩니다... 이 상황을 방지하기 위해서는 public한 생성자를
private하게 만들어줄 필요가 있습니다!
<br></br>

```java
public class GoodSingleton {
    private static GoodSingleton INSTANCE = getInstance();

    private GoodSingleton() {
    }

    public static GoodSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GoodSingleton();
        }
        return INSTANCE;
    }
}
```
<br></br>

```java
public class SingletonTestSample {
    public static void main(String[] args) {
//        GoodSingleton goodSingleton = new GoodSingleton(); 컴파일 에러!
        GoodSingleton instance1 = GoodSingleton.getInstance();
        GoodSingleton instance2 = GoodSingleton.getInstance();
        System.out.println(instance1.equals(instance2));
    }
}
```
private 생성자 덕분에 싱글턴임을 보증할 수 있게 되었습니다!

### PLUS. 직렬화
싱글턴을 [직렬화](https://techblog.woowahan.com/2550/)하는 작업을 하기 위해서는 추가 작업이 필요합니다.  
왜냐하면 **역직렬화 시 싱글턴을 보장할 수 없기 때문**입니다! (매번 새로운 인스턴스 생성) 이를 방지하기 위해서 readResolve() 메서드를 통해  
싱글턴을 보장해야 합니다.
```java
private Object readResolve(){
    return INSTANCE;
}
```


## Enum을 통해 싱글턴 보증
두번째 방법으로는 enum을 사용하는 방법입니다.
```java
public enum SingletonByEnum{
    INSTANCE;
}
```
단 만들려는 싱글턴이 Enum 외의 클래스를 상속해야 한다면 이 방법은 사용할 수 없습니다.