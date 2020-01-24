package generics;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		List<Object> objectList = new ArrayList<>();
		List<Number> numberList = new ArrayList<>();
		List<Double> doubleList = new ArrayList<>();

		m1(objectList);
		m1(numberList);
		// m1(doubletList); // ? super Number

		// m2(objectList); // ? extends Number
		m1(objectList);
		m1(objectList);
	}

	public static void m1(List<? super Number> list) {
		System.out.println(list);
		list.add(new Double(6.66));
		Object o = new Object();
		// list.add(o); //
	}

	public static void m2(List<? extends Number> list) {
		System.out.println(list);
		// list.add(new Integer(10)); Can't add with extends
	}
}
