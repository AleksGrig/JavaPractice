package jvm;

class A {
	int a = 1;

	static void staticMethod() {
		System.out.println("ClassA: staticMethod()");
	}

	void instanceMethod() {
		System.out.println("ClassA: instanceMethod()");
	}
}

class B extends A {
	int a = 2;

	static void staticMethod() {
		System.out.println("ClassB: staticMethod()");
	}

	@Override
	void instanceMethod() {
		System.out.println("ClassB: instanceMethod()");
	}
}

public class MethodTable {

	public static void test(A a) {
		a.staticMethod(); // Static methods are not overriden
		a.instanceMethod();
		System.out.println(a.a); // Variables are not overriden
	}

	public static void main(String[] args) {
		A b = new B();
		test(b);
	}
}
