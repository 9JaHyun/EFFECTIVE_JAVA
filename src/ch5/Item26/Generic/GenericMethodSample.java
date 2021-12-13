package ch5.Item26.Generic;

import java.util.Comparator;
import java.util.List;

/**
 * 	메서드를 제네릭하게 선언
 * 		- WildCard처럼 타입을 두루뭉실하게 하는 것 보다는 명시적으로 메서드 선언 및 타입을 지정하면
 * 			보다 견고한 코드를 작성할 수 있다.
 *  제네릭 메서드 만들기
 *  제네릭 메서드란 메서드의 선언부에 타입 변수를 사용한 메서드를 의미함.
 *  이때 타입 변수 선언은 메서드 선언부에 [반환 타입 바로 앞에 위치함.]
 *
 */
public class GenericMethodSample{
	// Collection sort()
	public static <T> void sort(List<T> list, Comparator<? super T> c) {
		list.sort(c);
	}
}
