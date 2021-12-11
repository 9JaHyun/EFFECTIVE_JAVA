# 3장. 모든 객체의 공통 메서드
모든 클래스드의 슈퍼 클래스인 `Object`의 final이 아닌 메서드들 `equals`, `hashcode`, `toString`
`clone`, `finalize`들은 모두 재정의를 염두해 설계된 메서드들입니다.  
그렇기 때문에 JAVA 진영에서도 이를 재정의하는데 지켜야 할 규약을 명확하게 정의해놓았습니다. 이를 무시한다면 예상치 못한 곳에서 문제가 발생할 수 있기 떄문에
주의를 기울여야 합니다. 추가적으로 이 책에서는 `compareTo` 역시 비슷한 맥락으로 판단하여 이 장에 넣었다고 합니다.

## 목차
### [Item10. equals는 일반 규약을 지켜 재정의하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch3/Item10)
### [Item11. equals를 재정의하려거든 hashCode도 재정의하다](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch3/Item11)
### [Item12. toString을 항상 재정의하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch3/Item12)
### [Item13. clone 재정의를 주의하여 진행하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch3/Item13)
### [Item14. Comparable을 구현할지 고려하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch3/Item14)
