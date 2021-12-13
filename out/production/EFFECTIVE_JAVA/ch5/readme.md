# 제네릭
JAVA5 이전... 컬렉션에서 요소를 꺼낼 때 마다 형변환을 했어야 했습니다.
왜냐하면 컴파일러가 이를 잡아주고 체크할 수단이 없기 때문입니다. 그렇게 JAVA5 이후
제네릭이 등장하면서 컬렉션이 담을 수 있는 타입을 미리 컴파일러에게 알려주어 들어갈 요소들을 한정할 수 있게 되었습니다.
덕분에 더욱 안전하게 설계가 가능해지게 되었습니다.  
  
추가적으로 컬렉션이 꼭 아니더라도 이 이점을 누릴 수 있으나 코드가 복잡해진다는 것을 각오하셔야 합니다.  
이번장에는 컬렉션에 국한된 것이 아니라 다른 객체들에도 제네릭을 사용하는 방법에 대해 배워봅시다.

## 목차
### [Item26. 로 타입은 사용하지 말라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item26)
### [Item27. Public 클래스에서는 Public 필드가 아닌 접근자 메서드를 사용하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item27)
### [Item28. 변경 가능성을 최소화하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item28)
### [Item29. 상속보다는 컴포지션을 사용하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item29)
### [Item30. 상속을 고려해 설계하고 문서화하라. 그러지 않았다면 상속을 금지하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item30)
### [Item31. 추상 클래스보다는 인터페이스를 우선하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item31)
### [Item32. 인터페이스는 구현하는 쪽을 생각해 설계하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch4/Item32)
### [Item33. 인터페이스는 타입을 정의하는 용도로 사용하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch4/Item33)
### [Item34. 태그가 달린 클래스보다는 클래스 계층구조를 활용하라 ](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch4/Item34)
### [Item35. 멤버 클래스는 되도록 static 하게 만들어라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch4/Item35)
### [Item36. 최상위 클래스는 한 파일에 하나만 담아라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch4/Item36)