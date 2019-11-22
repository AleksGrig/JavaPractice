package basics;

public class TypeCasting {

	public static void main(String[] args) {
		int oldVal = 1234567890;
		float f = oldVal;
		int newVal = (int) f; // Losing precision with implicit cast!!!
								// Possible: int -> float, long -> float, long -> double
		System.out.println(newVal);
	}

}
