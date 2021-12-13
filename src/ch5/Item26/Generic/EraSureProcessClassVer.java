package ch5.Item26.Generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 	제네릭의 타입 소거 (Erasure)
 * 		- Erasure란 원소 타입을 컴파일 타임에서만 검사를 하고 런타임에서는 해당 타입 정보를 알 수 없다.
 * 		  즉 컴파일 상태에만 제약 조건을 적용하고, 런타임에는 타입에 대한 장보를 소거하는 프로세스
 * 
 * 		- 제네릭 타입에서는 해당 타입 파라미터나 Object를 변경해준다.
 * 			- Object로 변경하는 경우 unbounded 된 경우를 뜻하며 <E extends Comparable>와 같이 bound드를 해주지 않은 경우를 의미.
 * 				- 이 소거 규칙에 대한 바이트 코드는 제네릭을 적용할 수 있는 일반 클래스, 인터페이스, 메서드에 적용이 가능.
 * 		- 타입 안정성 보존을 위해 필요시 type casting을 넣어준다.
 * 		- 확장된 제네릭 타입에서 다형성을 보존하기 위해 bridge method를 생성한다.
 *
 */
public class EraSureProcessClassVer {
	// Compile Error
//	List<Object> list = new ArrayList<Integer>();
//	list.add("Adding");	// type 불일치로 인해 add 실패

	// 실제로 이런 경우 선언 방식에 따라 컴파일러가 E를 Object로 변경한다.
	public static <E> boolean containsElement(E [] elements, E element) {
		for(E e : elements) {
			if(e.equals(element)) return true;
		}
		return false;
	}
	
	// 변경된 erasure (이렇게 런타임 오류를방지한다.)
	public static boolean erasureContainsElement(Object [] elements, Object element) {
		for(Object e : elements) {
			if(e.equals(element)) return true;
		}
		return false;
	}
}


class Stack<E> {
    private E[] stackContent;

    public Stack(int capacity) {
        this.stackContent = (E[]) new Object[capacity];
    }

    public void push(E data) {
        // ..
    }

    public E pop() {
        // ..
        return null;
    }
}

// 언바인딩의 경우에는 최상위 클래스 Object로 변경
class ErasureStack {
    private Object[] stackContent;

    public ErasureStack(int capacity) {
        this.stackContent = (Object[]) new Object[capacity];
    }

    public void push(Object data) {
        // ..
    }

    public Object pop() {
        // ..
        return null;
    }
}

class BoundStack<E extends Comparable<E>> {
    private E[] stackContent;

    public BoundStack(int capacity) {
        this.stackContent = (E[]) new Object[capacity];
    }

    public void push(E data) {
        // ..
    }

    public E pop() {
        // ..
        return null;
    }
}

// BoundStack의 경우 첫 번째 바인딩 된 클래스인 Comparable로 변경됨.
class ErasureBoundStack {
    private Comparable [] stackContent;

    public ErasureBoundStack(int capacity) {
        this.stackContent = (Comparable[]) new Object[capacity];
    }

    public void push(Comparable data) {
        // ..
    }

    public Comparable pop() {
        // ..
        return null;
    }
}
