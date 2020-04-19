package concurrency.miscellaneous;

public class ThreadCreation {

	public static void main(String[] args) {
		var thread = new MyThread();
		thread.start();
		try {
			thread.join(); // Using join to wait for thread to end
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("End of main");
	}

	private static class MyThread extends Thread {

		@Override
		public void run() {
			for (int i = 10; i > 0; i--) {
				System.out.println("i= " + i);
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
