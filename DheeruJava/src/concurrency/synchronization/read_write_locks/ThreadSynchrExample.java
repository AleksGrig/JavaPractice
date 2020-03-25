package concurrency.synchronization.read_write_locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSynchrExample {

	private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

	private static String message = "a";

	public static void main(String[] args) throws InterruptedException {
		Thread t2 = new Thread(new WriteA());
		Thread t3 = new Thread(new WriteB());

		t2.start();
		t3.start();
		for (int i = 0; i < COUNT; i++) {
			Thread t1 = new Thread(new Read());
			t1.start();
		}
	}

	static class Read implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i <= COUNT; i++) {
				if (lock.isWriteLocked()) {
					System.out.println("Lock for Writing");
				}
				lock.readLock().lock();
				try {
					System.out.println("Reading");
					Thread.sleep(1000);
				} catch (Exception e) {
				}
				System.out.println("ReadThread " + Thread.currentThread().getId() + " ---> Message is " + message);
				lock.readLock().unlock();
			}
		}
	}

	private static final int COUNT = 5;

	static class WriteA implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i <= COUNT; i++) {
				try {
					lock.writeLock().lock();
					System.out.println("I take the lock for 'a'");
					message = message.concat("a");
					Thread.sleep(1000);
				} catch (Exception e) {
				} finally {
					lock.writeLock().unlock();
				}
			}
		}
	}

	static class WriteB implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i <= COUNT; i++) {
				try {
					lock.writeLock().lock();
					System.out.println("I take the lock for 'b'");
					message = message.concat("b");
					Thread.sleep(1000);
				} catch (Exception e) {
				} finally {
					lock.writeLock().unlock();
				}
			}
		}
	}
}
