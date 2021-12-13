package ch5.Item26.Generic;

import java.util.List;

/**
 * 제네릭으로 구현된 메서드의 경우 선언된 타입으로만 매개변수를 입력해야 함.
 * 이를 상속받은 클래스 or 부모 클래스를 사용하고 싶어도 불가능.
 * 이러면 유연성이 크게 떨어진다. 그래서 WildCard를 사용!
 *
 */
public class UnboundedWildCardSample {
	
	/**
	 * Unbounded WildCard
	 * <?>만으로 정의를 내리는 경우. 내부적으로 Object로 정의되어 사용되고
	 * 모든 타입의 인자를 받을 수 있다. 
	 * 	- 사용처
	 * 		- Object 메소드에서 제공하는 기능으로만 구현하는 경우
	 * 		- 타입 파라미터에 의존하지 않는 메서드만 사용하는 경우 (List.size(), List.clear())
	 */
    private static void printList(List<?> list) {
        System.out.println(list);
    }
    
    

}
