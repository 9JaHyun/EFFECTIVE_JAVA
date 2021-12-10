# Item2. 생성자에 매개변수가 많다면 빌더를 고려하라
* 생성자나 생성자 팩토리 메서드나 모두 동일한 약점을 가지고 있습니다.
* 바로 **파라미터가 많아지면 생산성이 떨어진다**입니다.

## 파라미터가 많아지면 생산성이 떨어진다?
### 1. 확장성이 떨어진다.

```java
import ch1.Item2.NutritionFactsV2;

public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    // 점층적 생성자 패턴
    public NutritionFacts(int servingSize, int servings, int calories){
        this(servingSize, servings, calories, 0, 0, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}
```
* 영양 성분표의 정보를 가지는 `NutritionFacts`클래스가 있습니다.
* 만약 모든 영양성분을 채울 필요 없이 필요한 영양성분만 채운다면 그에 대한 생성자의 갯수는 셀 수 없이 많아지게 됩니다.
* 더군다나 생성자는 같은 시그니처에 대해 중복이 불가능하게 됩니다.
* 이 문제점들을 위해 **점층적 생성자 패턴**을 활용하지만 크게 와닿을 정도로 효과적이진 않아 보입니다.
* 이렇게 기존의 방법으로는 확장성을 키울 방법이 보이지 않습니다.

### 2. 실수할 가능성이 많아진다.
* 코드를 정상적으로 작동시키고 싶다면 파라미터들이 모두 정확하게 입력되야 합니다.
* 위 코드처럼 6개 정도면 그나마 양반이지, 만약 입력해야할 파라미터가 10개가 넘어가게 된다면 어떻게 해야 할까요?
* 실수할 가능성은 계속해서 높아지게 됩니다.

### 3. 일관성이 무너지게 된다.
* 위 두가지 문제를 해결하기 위해 자바빈즈(`getter`, `setter`)를 사용한다 해봅시다.
```java
NutritionFacts pizza = new NutritionFacts();
pizza.setServingSize(1000);
pizza.setServings(8);
pizza.serCalories(2000);
pizza.setSodium(40);
Pizza.setCarbohydrate(180);
```
* 타이핑으로 인한 실수는 줄어들게 될지 몰라도, 객체가 완전히 생성되기 전까지 일관성이 무너진 상태라는 문제점이 생깁니다.
* 이 문제로 인해 **클래스를 불변으로 만들 수 없고**, **스레드 안전성에도 추가적인 주의**를 기울여야 합니다.

## 빌더 패턴의 등장
* 해당 문제를 해결하기 위해 **빌더 패턴**이 등장하게 되었습니다.
* 빌더 패턴은 점층적 생성자 패턴과, 자바빈즈를 합친 방법입니다.

```java
public final class NutritionFactsV2 {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder {
        // 필수 매개변수
        private final int servingSize;
        private final int servings;

        // 선택 매개변수
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        // 자바 빈즈와 유사
        public Builder calories(int value) {
            this.calories = value;
            return this;
        }
        ...
    }
    
    private NutritionFactsV2(Builder builder) {
        this.servingSize = builder.servingSize;
        this.servings = builder.servings;
        this.calories = builder.calories;
        this.fat = builder.fat;
        this.sodium = builder.sodium;
        this.carbohydrate = builder.carbohydrate;
    }
}
```
* 원리는 간단합니다. 
  1. **Builder라는 중첩 클래스를 생성**해 **선택 매개변수**만 자바빈즈와 같은 형식으로 입력을 하면서 Builder 자신을 반환
  2. 마지막으로 외부에서 중첩 클래스를 매개변수로 받는 생성자를 만들어 불변 클래스를 생성.

## 빌더 패턴의 단점?
### 코드의 장황함
* 위 코드를 보면 상당이 장황한 것을 볼 수 있습니다.
* 이렇게 초기 셋업에 대한 피곤함을 감수해야 하는 단점이 있습니다. (하지만 나중에는 편해지니 감수할만 하죠?)
* 추가적으로 Lombok에서는 빌더패턴을 자동으로 짜주는 @Builder 어노테이션을 지원하고 있습니다.

### 성능?
* 빌더 클래스의 생성 비용 자체는 그렇게 크지 않습니다.
* 하지만 성능에 굉장히 민감한 코드를 짜게 된다면 문제가 될 수 있기 때문에 Trade-Off를 잘 따져봐야 합니다.

## 결론
* 매개변수가 처음부터 많거나, 계속해서 늘어날 조짐이 보인다면 초장에 빌더패턴을 채택하는 것이 좋습니다.
* 비용이 크게 들지는 않으나, 성능에 민감한 경우라면, 빌더 패턴을 재고해라
* 다른 코드로 마이그레이션을 할 필요가 없다면 Lombok의 @Builder 사용을 고려하라.