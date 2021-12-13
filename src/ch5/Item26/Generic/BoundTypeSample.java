package ch5.Item26.Generic;

/**
 * 바운디드 타입
 * 특정 타입의 서브 타입으로 제한.
 * 클래스나 인터페이스를 설계할 때 가장 흔하게 사용됨.
 */

// BoundTypeSample의 타입으로 Number의 서브타입만 허용!
public class BoundTypeSample<T extends Number> {
	public void set(T value) {
		
	}
	
	public static void main(String[] args) {
		// Integer은 Number의 서브타입이기 때문에 괜찮다.
		BoundTypeSample<Integer> boundTypeSample = new BoundTypeSample<>();

		boundTypeSample.set(123);
		// 문자열은 cut!
//		boundTypeSample.set("hello");
	}

}
