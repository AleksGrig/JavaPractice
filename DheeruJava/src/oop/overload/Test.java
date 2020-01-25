package oop.overload;

public class Test {

	public static void main(String[] args) {
		A b = new B();
		b.apply(3); // Java invokes method from class A, because method apply(int) isn't overriden
					// in class B, it's apply(long) there

		B bb = new B();
		bb.apply(100L); // If reference is B then we have access to both methods
	}

}
