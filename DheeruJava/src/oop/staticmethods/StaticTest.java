package oop.staticmethods;

public class StaticTest {

	public static void invokation(A a) {
		System.out.println("/nInside invokation method: ");
		a.staticMethod1();
		a.staticMethod2();
	}

	public static void main(String[] args) {
		B b = new B();
		b.staticMethod1(); // Not recommended invoke static methods on objects
		B.staticMethod1(); // static methods are inherited from parent classes
		A.staticMethod1();
		b.staticMethod2();
		B.staticMethod2();
		A.staticMethod2();

		invokation(b);
		I.staticInterfaceMethod();
		// b.staticInterfaceMethod(); // Can't invoke interface static methods on
		// objects, only using interface name
		// B.staticInterfaceMethod(); // Also impossible
	}

}
