package concurrency.synchronization.notify_notifyAll;

public class SynchronizationExample {

	public static void main(String[] args) throws InterruptedException {
		var manager = new Manager();
		for (int i = 0; i < 5; i++) {
			var thread = new Thread1(manager);
			thread.start();
		}
		Thread.sleep(2000);
		manager.startAllThreads();
		// manager.startOneThread();
	}
}
