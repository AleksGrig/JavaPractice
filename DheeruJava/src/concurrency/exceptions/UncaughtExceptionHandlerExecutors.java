package concurrency.exceptions;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class UncaughtExceptionHandlerExecutors {
	
	private static class ExceptionThreadFactory implements ThreadFactory {
		private static int count = 1;
		private static String NAME =  "MyThread-";

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r, NAME + count++);
			t.setUncaughtExceptionHandler(new ThreadExceptionHandler("HANDLER1"));
			return t;
		}		
	}
	
	private static class ExceptionLeakingTask implements Runnable {
		@Override
		public void run() {
			throw new RuntimeException();
		}		
	}
	
	private static class ThreadExceptionHandler implements UncaughtExceptionHandler {
		private String handlerId;
		
		private ThreadExceptionHandler(String handlerId) {
			this.handlerId = handlerId;
		}
		
		private ThreadExceptionHandler() {}
		
		@Override
		public void uncaughtException(Thread t, Throwable e) {
			System.out.println(this + "caught Exception in thread " + t.getName() + " => " + e);
		}		
		
		@Override
		public String toString() {
			return this.getClass().getSimpleName() + "[ " + handlerId + "]";
		}
	}

	public static void main(String[] args) {
		String currentThread = Thread.currentThread().getName();
		System.out.println("[" + currentThread + "] " + "main thread starts");
		Thread.setDefaultUncaughtExceptionHandler(new ThreadExceptionHandler("DEFAULT HANDLER"));
		
		ExecutorService service1 = Executors.newCachedThreadPool();
		service1.execute(new ExceptionLeakingTask());
		service1.execute(new ExceptionLeakingTask());
		service1.execute(new ExceptionLeakingTask());
		
		ExecutorService service2 = Executors.newCachedThreadPool(new ExceptionThreadFactory());
		service2.execute(new ExceptionLeakingTask());
		service2.execute(new ExceptionLeakingTask());
		service2.execute(new ExceptionLeakingTask());
		
		service1.shutdown();
		service2.shutdown();
		
		System.out.println("[" + currentThread + "] " + "main thread ends");
	}
}
