package concurrency.exceptions;

import java.lang.Thread.UncaughtExceptionHandler;

public class UncaughtExceptionHandlerExample {

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
		
		
		Thread t1 = new Thread(new ExceptionLeakingTask(), "Thread1");
		t1.setUncaughtExceptionHandler(new ThreadExceptionHandler("T1 HANDLER"));
		t1.start();
		new Thread(new ExceptionLeakingTask(), "Thread2").start();
		new Thread(new ExceptionLeakingTask(), "Thread3").start();
		new Thread(new ExceptionLeakingTask(), "Thread4").start();
		
		System.out.println("[" + currentThread + "] " + "main thread ends");
	}

}
