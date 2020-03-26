package concurrency.synchronization.supplier_consumer;

public class Resource {

	public static final int COUNT = 5;
	private int value;
	private boolean empty = true;

	public synchronized void put(int value) {
		if (!empty) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Put " + value);
		this.value = value;
		empty = false;
		notifyAll();
	}

	public synchronized int get() {
		if (empty) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		empty = true;
		System.out.println("Get " + value);
		notifyAll();
		return value;
	}
}
