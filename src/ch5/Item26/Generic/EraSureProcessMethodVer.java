package ch5.Item26.Generic;

/**
 * Method Type Erasure의 경우 method-level type erasure가 저장되지 않고
 * 바인딩되지 않은 경우 부모 형식이 Object로 변환되거나, 바인딩된 경우 첫 번째 바인딩 된 클래스로 변환된다.
 *
 *
 */
public class EraSureProcessMethodVer {
	
	// Unbounded
	public static <E> void printArray(E[] array) {
		for(E element : array) {
			System.out.printf("%s \n", element);
		}
	}
	
	// unbound Erasure는 Object로 변경
	public static void erasurePrintArray(Object[] array) {
		for(Object element : array) {
			System.out.printf("%s \n", element);
		}
	}
	
	// Bounded
	public static <E extends Comparable<E>> void printArray(E[] array) {
	    for (E element : array) {
	        System.out.printf("%s ", element);
	    }
	}
	
	// 첫번째 바운딩인 Comparable로 변환
	public static void erasuerPrintArray(Comparable[] array) {
	    for (Comparable element : array) {
	        System.out.printf("%s ", element);
	    }
	}
}
