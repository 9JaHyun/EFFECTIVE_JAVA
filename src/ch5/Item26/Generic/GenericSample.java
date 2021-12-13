package ch5.Item26.Generic;

public class GenericSample<T> {
	T element;
	
	public static void main(String[] args) {
		GenericSample<Integer> integerSample = new GenericSample<>();
		GenericSample<String> stringSample = new GenericSample<>();
		integerSample.setElement(3);
		stringSample.setElement("Hello");
		
		System.out.println(integerSample.getElement());
		System.out.println(stringSample.getElement());
		
		
	}
	
	public void setElement(T element) {
		this.element = element;
	}
	
	public T getElement() {
		return element;
	}

}
