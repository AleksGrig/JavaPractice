package oop.diamond;

public interface I1 {
	public void method1();

	default void method2() {
		System.out.println("Interface1 - default method2()");
	}
}
