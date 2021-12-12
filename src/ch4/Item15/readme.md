# 클래스와 멤버의 접근 권한을 최소화하라
잘 설계된 컴포넌트의 특징은 여러가지가 있으나, 하나를 꼽자면 바로 클**래스가 가지고 있는 정보와 구체적인 동작방식을 외부로부터 얼마나 잘 숨겼냐** 입니다.
이 척도를 `정보 은닉`, `캡슐화` 라고 하는데 대단히 중요한 개념이다.


## 정보 은닉, 캡슐화의 장점
#### 1. 모두 독립적이기 때문에 병렬성을 이용해 시스템 개발 속도를 높일 수 있다.
#### 2. 모두 독립적이기 때문에 더욱 단순하게 그 객체 하나만 이해하면 된다. 그래서 경제적이다.
#### 3. 정보 은닉 자체가 성능을 높여주지는 않지만, 책임 역할의 분리 덕분에 성능 향상을 위해 하나의 기능에 집중이 가능하다.
#### 4. 재사용성을 높여준다.
#### 5. 결국 엔터프라이즈 제작의 난이도를 낮춰준다.


## 정보 은닉, 캡슐화 원칙
이를 지킬 수 있는 가장 기본적인 방법은 바로 **모든 클래스와 멤버의 접근성을 가능한 좁히는 것**입니다.
최상위 클래스와 인터페이스에 부여할 수 있는 접근 수준은 `package-private`, `public` 두가지 입니다.
  * public : **공개 API(퍼블릭 인터페이스)로 선언**하는 접근 제한자
  * package-private : **해당 메서드를 선언한 클래스 내부에서만 사용할때 선언**하는 접근 제한자.

그래서 최대한 클라이언트 코드에서 사용하느 코드들을 제외하고는 모두 `package-private`하게 만드는 것이 좋습니다.
문제점은 상위 클래스의 메서드를 재정의 시 접근 수준을 상위 클래스에서보다 좁게 설정할 수 없다는 것 입니다.  
(이는 **OOP의 기본원칙** 때문인데, **부모 클래스의 완전한 인스턴스이므로 적어도 부모 클래스와 동일한 인터페이스를 제공해야 하기 때문**입니다. 
그렇기 때문에 이 퍼블릭 인터페이스를 더 좁은 접근제한자로 감춘다는 것은 즉 기본원칙을 위반하는 것이기 때문에 더 좁게 설정이 불가능합니다.
이를 **리스코프 치환 원칙**이라고 합니다.)


### 깨알정보 : 접근 제한자 종류
다들 아실테지만, 접근 제한자 종류를 한번 더 짚고 넘어가겠습니다.
* `private` : 멤버를 선언한 최상위 클래스에서만 접근이 가능하다.
* `package-private` : 멤버가 소속된 패키지 안의 모든 클래스에서 접근이 가능하다.
* `protected` : package-private의 접근 범위를 포함하며, 이 멤버를 선언한 클래스의 하위 클래스에서도 접근할 수 있다.
* `public` : 모든 곳에서 접근할 수 있다.

### 주의사항
여러 주의사항이 있지만 이 점들만 유의하시면 좋습니다.
* `Serializable`를 구현한 클래스에서는 필드들이 의도치 않게 공개 API가 될 수 있다.
* public 클래스의 인스턴스 필드는 되도록 public이 아니어야 한다.
  * 불변식 보장을 못한다.
  * 스레드에 안전하지 않다.
* 길이가 0이 아닌 배열은 모두 변경이 가능하다. (`Be_carefulAtCollection.class`)
  * 그렇기 때문에 배열을 public static final로 두는 일은 없어야 한다.
  * 배열은 private하게 두고
    * 불변 리스트를 생성하는 팩토리 메서드 (`unmodifiableList()`)를 이용하거나
    * 방어적 복사(`clone()`)를 사용하자.
    
## 결론
* 요소들의 접근성은 가능한 최소한으로 해라.
* 꼭 공개로 돌려야 하는 것들만 public API로 설계하자.
* public 클래스는 상수용 public static final 필드 외에는 어떠한 public 필드도 가져서 안된다.
* 추가적으로 public static final을 붙였다 하더라도 객체가 불변을 유지할 수 있는지 확인하라! (배열)