package concurrency.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class OneTimeExecution {
	
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");
	
	private static class ScheduledRunnable implements Runnable {
		private long sleepTime;
		private static int count = 1;
		private String taskId = "SheduledRunnable-" + count++;
		
		private ScheduledRunnable(long sleepTime) {
			this.sleepTime = sleepTime;
		}

		@Override
		public void run() {
			Date startTime = new Date();
			String currentThreadName = Thread.currentThread().getName();
			System.out.println("[" + currentThreadName + "]" + "<" + taskId + ">" + " STARTED AT " + 
					dateFormatter.format(startTime));
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("[" + currentThreadName + "]" + "<" + taskId + ">" + " FINISHED AT " + 
					dateFormatter.format(new Date()));
		}		
	}
	
	private static class ScheduledCallable implements Callable<Integer> {
		private int a;
		private int b;
		private long sleepTime;
		private static int count = 1;
		private String taskId = "SheduledCallable-" + count++;
		
		private ScheduledCallable(int a, int b, long sleepTime) {
			this.a = a;
			this.b = b;
			this.sleepTime = sleepTime;
		}

		@Override
		public Integer call() throws Exception {
			Date startTime = new Date();
			String currentThreadName = Thread.currentThread().getName();
			System.out.println("[" + currentThreadName + "]" + "<" + taskId + ">" + " STARTED AT " + 
					dateFormatter.format(startTime));
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("[" + currentThreadName + "]" + "<" + taskId + ">" + " FINISHED AT " + 
					dateFormatter.format(new Date()));
			
			return a + b;
		}		
	}
	
	private static class NamedThreadsFactory implements ThreadFactory {

		private static int id = 0;
		private static final String NAME = "MyThread-";
		
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, NAME + ++id);
		}		
	}

	public static void main(String[] args) throws Exception{		
		String currentThread = Thread.currentThread().getName();
		System.out.println("[" + currentThread + "] " + "main thread starts");
		
		ScheduledExecutorService service = Executors.newScheduledThreadPool(4, new NamedThreadsFactory());
		System.out.println("[" + currentThread + "] current time>>" + dateFormatter.format(new Date()));
		
		ScheduledFuture<?> future1 = service.schedule(new ScheduledRunnable(5000), 4, TimeUnit.SECONDS);
		ScheduledFuture<Integer> future2 = service.schedule(new ScheduledCallable(2, 3, 0), 6, TimeUnit.SECONDS);
		ScheduledFuture<?> future3 = service.schedule(new ScheduledRunnable(0), 8, TimeUnit.SECONDS);
		ScheduledFuture<Integer> future4 = service.schedule(new ScheduledCallable(3, 4, 0), 10, TimeUnit.SECONDS);
		
		service.shutdown();
		// future1.cancel(true);
		
		System.out.println("[" + currentThread + "] " + "Retrieving results now...\n");
		System.out.println("[" + currentThread + "] " + "TASK-1 RESULT = " + future1.get() + "\n");
		System.out.println("[" + currentThread + "] " + "TASK-2 RESULT = " + future2.get() + "\n");
		System.out.println("[" + currentThread + "] " + "TASK-3 RESULT = " + future3.get() + "\n");
		System.out.println("[" + currentThread + "] " + "TASK-4 RESULT = " + future4.get() + "\n");
		
		System.out.println("[" + currentThread + "] " + "main thread ends");
	}

}
