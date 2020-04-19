package concurrency.miscellaneous;

import java.util.ArrayList;
import java.util.List;

// As alternative we can simply use join mechanism
public class ClassicReturnResult {
	
	private static class SumTask implements Runnable {

		private static int count;
		public String id = "Task-" + ++count;
		private int a;
		private int b;
		private long sleepTime;
		private int result;
		private volatile boolean isDone = false;
		
		private SumTask(int a, int b, long sleepTime) {
			this.a = a;
			this.b = b;
			this.sleepTime = sleepTime;
		}
		
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + ": " + id + " STARTS");
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result = a + b;
			isDone = true;
			synchronized (this) {
				this.notifyAll();
			}
			
			System.out.println(Thread.currentThread().getName() + ": " + id + " ENDS");
		}
		
		public int getResult() {
			if(!isDone) {
				synchronized(this) {
					System.out.println(Thread.currentThread().getName() + ": " + id + " WAITS");
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			return result;
		}
	}

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName() + " STARTS");
		
		List<Runnable> tasks = new ArrayList<>();
		for(int i=0; i<3; i++) {
			tasks.add(new SumTask(i, i+1, 1000 * (i +1)));
		}		
		for (Runnable task : tasks) {
			new Thread(task).start();
		}		
		for (Runnable task : tasks) {
			System.out.println(((SumTask)task).id + " result is " + ((SumTask)task).getResult());
		}
		
		System.out.println(Thread.currentThread().getName() + " ENDS");
	}

}
