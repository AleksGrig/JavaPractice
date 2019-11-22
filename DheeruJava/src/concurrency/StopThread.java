package concurrency;

import java.util.concurrent.TimeUnit;

public class StopThread {
	private static volatile boolean stop;

	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stop) {
					System.out.println("In while ...");
				}
			}
		}).start();

		TimeUnit.SECONDS.sleep(1);
		System.out.println("stop = true");
		stop = true;
	}
}

