package ch5.Item26.Generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Upper Bounded Wildcard
 * <? extends Foo>와 같은 형태로 사용하고, 특정 클래스의 자식 클래스만을 인자로 받는다는 것!
 * 임의의 Foo을 상속받는 어느 클래스가 와도 되지만, 사용할 수 있는 기능은 Foo 클래스에 한정된다.
 * 주로 변수의 제한을 완화하기 위해 사용된다.
 */

/**
 * Lower Bounded Wildcard
 * <? super Foo>와 같은 형태로 사용되고, Upper bounded와는 반대로 특정 클래스의 부모 클래스만을
 * 인자로 받겠다는 선언이다.
 * 예시는 Foo 클래스의 부모인 어떤 객체도 인자로 올 수 있으나, 사용할때는 Object로 취급된다.
 */
public class UpperAndLower {
	public <T extends Foo> void test(List<T> fooList) {
		fooList.get(0).foo();
		// Foo를 상속받았으나, Var의 존재를 알 수 없기 때문에 불가능.
		// 앞서 말한 Foo 클래스에 한정된다는 말이 바로 이것.
//		fooList.get(0).var();
		
	}
	
	// Upper Bound
	List<? extends Foo> upperList = new ArrayList<>();
	
	// Lower Bound
	List<? super Var> lowerList = new ArrayList<>();
	
	public void upperLowerTest() {
		UpperAndLower upperAndLower = new UpperAndLower();
		upperAndLower.test(upperList);
		// 상위 클래스에 Foo가 있다고 해도 넘길 수 없다.
//		upperAndLower.test(lowerList);
	}


}
class Foo {
	public void foo() {
		System.out.println("Do Something by Foo");
	}
}

class Var extends Foo{
	public void var() {
		System.out.println("Do Something by Var");
	}
}