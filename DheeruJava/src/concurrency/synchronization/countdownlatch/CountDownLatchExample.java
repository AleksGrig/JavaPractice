package concurrency.synchronization.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

	public static void main(String[] args) throws InterruptedException {
		final int N = 5;

		var startSignal = new CountDownLatch(1);
		var doneSignal = new CountDownLatch(N);

		for (int i = 0; i < N; i++) {
			new Thread(new Woker(startSignal, doneSignal)).start();
		}

		doSomethingElse(1); // don't let run yet
		startSignal.countDown(); // let all threads proceed
		doneSignal.await(); // wait for all to finish
		doSomethingElse(2);
	}

	private static void doSomethingElse(int phase) {
		try {
			Thread.sleep(2000);
			System.out.println("doSomethingElse is done, phase: " + phase);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Woker implements Runnable {

	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;

	public Woker(CountDownLatch startSignal, CountDownLatch doneSignal) {
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
	}

	@Override
	public void run() {
		try {
			startSignal.await();
			doWork();
			doneSignal.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void doWork() {
		try {
			Thread.sleep((int) (Math.random() * 2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread is done");
	}
}
