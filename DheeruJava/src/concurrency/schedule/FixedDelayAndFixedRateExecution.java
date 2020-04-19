package concurrency.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused"})
public class FixedDelayAndFixedRateExecution {
	
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
	
	private static class NamedThreadsFactory implements ThreadFactory {

		private static int id = 0;
		private String NAME = "MyThread-" + ++id;
		
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, NAME);
		}		
	}
	
	public static void main(String[] args) throws Exception{
		String currentThread = Thread.currentThread().getName();
		System.out.println("[" + currentThread + "] " + "main thread starts");
		//fixedDelayExecution(currentThread);
		fixedRateExecution(currentThread);
		System.out.println("[" + currentThread + "] " + "main thread ends");
	}
	
	private static void fixedRateExecution(String currentThread) throws Exception{
		ScheduledExecutorService service = Executors.newScheduledThreadPool(3, new NamedThreadsFactory());
		System.out.println("[" + currentThread + "] current time>>" + dateFormatter.format(new Date()));
		ScheduledFuture<?> future = service.scheduleAtFixedRate(new ScheduledRunnable(1000), 4, 2, TimeUnit.SECONDS);
		for (int i = 0; i < 10; i++) {
			long now = new Date().getTime();
			long delay = future.getDelay(TimeUnit.MILLISECONDS);
			System.out.println("[" + currentThread + "] " + "next run scheduled in approximately " + 
					dateFormatter.format(new Date(now + delay)));
			Thread.sleep(3000);
		}		
		service.shutdown();		
	}

	private static void fixedDelayExecution(String currentThread) throws Exception{
		ScheduledExecutorService service = Executors.newScheduledThreadPool(3, new NamedThreadsFactory());
		System.out.println("[" + currentThread + "] current time>>" + dateFormatter.format(new Date()));
		ScheduledFuture<?> future = service.scheduleWithFixedDelay(new ScheduledRunnable(1000), 4, 2, TimeUnit.SECONDS);
		for (int i = 0; i < 10; i++) {
			long now = new Date().getTime();
			long delay = future.getDelay(TimeUnit.MILLISECONDS);
			System.out.println("[" + currentThread + "] " + "next run scheduled in approximately " + 
					dateFormatter.format(new Date(now + delay)));
			Thread.sleep(3000);
		}		
		service.shutdown();		
	}
}
