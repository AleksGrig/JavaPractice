package assignments;

public class FizBuz {

	public static void main(String[] args) {
		fizBuz();
	}

	private static void fizBuz() {
		for (int i = 1; i <= 100; i++) {
			if (i % 3 == 0 && i % 5 == 0) {
				System.out.println("FizBuz");
			} else if (i % 3 == 0) {
				System.out.println("Fiz");
			} else if (i % 5 == 0) {
				System.out.println("Buz");
			} else {
				System.out.println(i);
			}
		}
	}
}
