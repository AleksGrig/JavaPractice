package basics;

public class MethodOverload {

	private static void go(int i) {
		System.out.println("go(int): " + i);
	}

	private static void go(short s) {
		System.out.println("go(short): " + s);
	}

	private static void go(float f) {
		System.out.println("go(float): " + f);
	}

	private static void go(double d) {
		System.out.println("go(double): " + d);
	}

	public static void main(String[] args) {
		go(1);
		byte b = 50;
		go(b);

		go(1.0);
		float f = 3.3f;
		go(f);
	}

}
