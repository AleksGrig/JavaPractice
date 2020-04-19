package concurrency.miscellaneous;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@SuppressWarnings(value = { "unused" })
public class ExecutorExample {

	private static class Task implements Runnable {

		private static int count = 0;
		private int id;

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
		}

		private Task() {
			this.id = ++count;
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
		fixedThreadPoolExample();
		//cashedThreadPoolExample();
		//singleThreadPoolExample();
	}
	
	private static void singleThreadPoolExample() {
		System.out.println("MAIN STARTS");
		var service = Executors.newSingleThreadExecutor(new NamedThreadsFactory());

		for (int i = 0; i < 4; i++) {
			service.execute(new Task());
		}
		
		service.shutdown();
		System.out.println("MAIN ENDS");
	}
	
	private static void cashedThreadPoolExample() {
		System.out.println("MAIN STARTS");
		var service = Executors.newCachedThreadPool(new NamedThreadsFactory());

		for (int i = 0; i < 4; i++) {
			service.execute(new Task());
		}
		
		service.shutdown();
		System.out.println("MAIN ENDS");
	}
	
	private static void fixedThreadPoolExample() {
		System.out.println("MAIN STARTS");
		var service = Executors.newFixedThreadPool(3, new NamedThreadsFactory());

		for (int i = 0; i < 4; i++) {
			service.execute(new Task());
		}
		
		service.shutdown();
		System.out.println("MAIN ENDS");
	}

}
