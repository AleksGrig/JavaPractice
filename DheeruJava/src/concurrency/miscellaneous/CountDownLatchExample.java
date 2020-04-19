package concurrency.miscellaneous;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CountDownLatchExample {
	
	private static class Task implements Runnable {

		private static int count = 0;
		private int id;
		private CountDownLatch countDown;

		@Override
		public void run() {
			System.out.println("START OF TASK " + id);
			for (int i = 10; i > 0; i--) {
				System.out.println(Thread.currentThread().getName() + " TaskId-" + id + " i= " + i);
				try {
					Thread.sleep((long) (Math.random() * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("END OF TASK " + id);
			
			if(countDown != null) {
				countDown.countDown();
				System.out.println("Latch Count Down equals to " + countDown.getCount());
			}
		}

		private Task(CountDownLatch countDown) {
			this.id = ++count;
			this.countDown = countDown;
		}
	}
	
	private static class NamedThreadsFactory implements ThreadFactory {

		private static int id = 0;
		private String NAME = "MyThread-";
		
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, NAME + ++id);
		}		
	}

	public static void main(String[] args) {
		String currentThread = Thread.currentThread().getName();
		System.out.println("[" + currentThread + "] " + "main thread starts");
		
		ExecutorService service = Executors.newCachedThreadPool(new NamedThreadsFactory());
		CountDownLatch done = new CountDownLatch(4);
		
		service.execute(new Task(done));
		service.execute(new Task(done));
		service.execute(new Task(done));
		service.execute(new Task(done));
		
		service.shutdown();
		try {
			System.out.println("[" + currentThread + "] " + "main thread waits");
			done.await();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("[" + currentThread + "] " + "main thread ends");
	}
}
