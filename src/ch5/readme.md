# 제네릭
JAVA5 이전... 컬렉션에서 요소를 꺼낼 때 마다 형변환을 했어야 했습니다.
왜냐하면 컴파일러가 이를 잡아주고 체크할 수단이 없기 때문입니다. 그렇게 JAVA5 이후
제네릭이 등장하면서 컬렉션이 담을 수 있는 타입을 미리 컴파일러에게 알려주어 들어갈 요소들을 한정할 수 있게 되었습니다.
덕분에 더욱 안전하게 설계가 가능해지게 되었습니다.  
  
추가적으로 컬렉션이 꼭 아니더라도 이 이점을 누릴 수 있으나 코드가 복잡해진다는 것을 각오하셔야 합니다.  
이번장에는 컬렉션에 국한된 것이 아니라 다른 객체들에도 제네릭을 사용하는 방법에 대해 배워봅시다.

## 목차
### [Item26. 로 타입은 사용하지 말라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item26)
### [Item27. 비검사 경고를 제거하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item27)
### [Item28. 배열보다는 리스트를 사용하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item28)
### [Item29. 이왕이면 제네릭 타입으로 만들라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item29)
### [Item30. 이왕이면 제네릭 메서드로 만들라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item30)
### [Item31. 한정적 와일드카드를 사용해 API 유연성을 높여라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch5/Item31)
### [Item32. 제네릭과 가변인수를 함께 쓸 때는 신중하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch4/Item32)
### [Item33. 타입 안전 이종 컨테이너를 고려하라](https://github.com/9JaHyun/EFFECTIVE_JAVA/tree/main/src/ch4/Item33)