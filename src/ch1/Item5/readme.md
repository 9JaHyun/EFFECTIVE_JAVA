# Item5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.
애플리케이션의 크기가 증가할수록 여러 자원을 받아와서 실행하는 경우가 많아집니다.  

```java
public class SpellChecker{
    private static final Korean dictionary = new Korean();

    private SpellChecker() {}

    public static void isValid(String word) {
        ...
    }

    public static void suggestions(String typo){
        ...
    }
}
class Korean {
    ...
}
```
위의 코드만 봐도 단순히 한국어 사전만 참고하지만 점점 많은 사전을 지원하면 할 수록 여러 자원을 받아야 할 필요가 생깁니다.
이렇게 되면 클래스 구성을 어떻게 만들어야 할까요? `SpellChecker`를 지원하는 사전마다 새로 만들어야 할까요?

```java
public class KoreanSpellChecker{ ... }

public class EnglishSpellChecker{
    private static final English dictionary = new English();
}

public class FrenchSpellchecker{
    private static final French dictionary = new French();
}

public class JapaneseSpellChecker{
    private static final Japanese dictionary = new Japanese();
}
...
```
한눈에 봐도.... 딱히 좋은 방법은 아닌 것 같습니다.
이런 상황에서는 인스턴스 생성 시 SpellChecker에 언어 정보를 넘겨주는 것이 훨씬 효과적입니다!
```java
public class SpellChecker{
    private Language dictionary;

    private SpellChecker(Language language) {
        this.dictionary = language;
    }

    public static SpellChecker initSpellChecker(String language) {
        return switch (language) {
            case "한국어" -> new SpellChecker(new Korean());
            case "영어" -> new SpellChecker(new English());
            case "프랑스어" -> new SpellChecker(new French());
            case "일본어" -> new SpellChecker(new Japanese());
            default -> null;
        };
    }

    public void isValid() {
        dictionary.info();
    }

    public void suggestions(){
        dictionary.info();
    }
}
```
이렇게 짜게 된다면 인스턴스 생성 시 언어 정보를 넘겨주면 되기 때문에 훨씬 확장에 열려 있습니다.
이러한 방식을 **의존 객체 주입 패턴**이라고 합니다!
이 패턴을 적극적으로 사용한 패턴이 있습니다. 바로 **팩토리 메서드 패턴**입니다!

* 실행결과
```java
public class SpellCheckerTestSample {
    public static void main(String[] args) {
        SpellChecker spellChecker1 = SpellChecker.initSpellChecker("한국어");
        SpellChecker spellChecker2 = SpellChecker.initSpellChecker("영어");
        SpellChecker spellChecker3 = SpellChecker.initSpellChecker("일본어");
        SpellChecker spellChecker4 = SpellChecker.initSpellChecker("프랑스어");

        spellChecker1.isValid();
        spellChecker2.isValid();
        spellChecker3.isValid();
        spellChecker4.isValid();
    }
}
```
[spellCheckerResult.png](./img/spellcheckerresult.png)

## 결론
* 클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다.
* 이 자원을 클래스가 직접 만들어서도 안된다. 
* 대신 필요한 자원을 생성자에 넘겨주어라! (의존 객체 주입)
* 자원을 생성하는 책임은 의도에 따라 나누면 된다.
* 의존 객체 주입 방법은 유연성을 개선시켜주나, 코드를 어지럽게 만들 가능성이 있다. 이럴때는 의존 객체 주입 프레임워크를 사용하는 것을 추천한다.