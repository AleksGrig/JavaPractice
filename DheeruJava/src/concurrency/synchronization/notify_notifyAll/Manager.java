package concurrency.synchronization.notify_notifyAll;

public class Manager {

	public synchronized void methodWait() {
		System.out.println("Thread is waiting: " + Thread.currentThread().getId());
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread is done: " + Thread.currentThread().getId());
	}

	public synchronized void startAllThreads() {
		System.out.println("StartAllThreads");
		notifyAll();
	}

	public synchronized void startOneThread() {
		System.out.println("StartOneThread");
		notify();
	}
}
