package basics;

public class ArraysDemo {

	static void threeDimensionalArrays() {
		System.out.println("\nInside threeDimensionalArrays ...");
		int[][][] unitsSold = new int[][][] { {
				// New York
				{ 0, 0, 0, 0 }, // Jan
				{ 0, 0, 0, 0 }, // Feb
				{ 0, 0, 0, 0 }, // Mar
				{ 0, 850, 0, 0 }// Apr
				}, {
						// San Francisco
						{ 0, 0, 0, 0 }, // Jan
						{ 0, 0, 0, 0 }, // Feb
						{ 0, 0, 0, 0 }, // Mar
						{ 0, 0, 0, 0 } // Apr
				}, { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
				{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } };

		System.out.println("unitsSold[0][3][1]: " + unitsSold[0][3][1]);
	}

	public static void main(String[] args) {
		threeDimensionalArrays();
	}

}
