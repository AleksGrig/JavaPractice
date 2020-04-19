package concurrency.executorframework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorExample {

	private static class Process implements Callable<String> {

		private static Integer number = 0;
		private String name = "Process" + number++;

		@Override
		public String call() throws Exception {
			System.out.println("START " + name);
			Thread.sleep(1000 + (int) (Math.random() * 2000));
			System.out.println("STOP " + name);
			return name + " result received";
		}
	}

	public static void main(String[] args) throws Exception {
		int processors = Runtime.getRuntime().availableProcessors();
		System.out.println("Machine has " + processors + " processors");
		// runnableExample();
		// callableExample();
		threadPoolExample();
	}

	private static void threadPoolExample() throws Exception {
		var executor = Executors.newFixedThreadPool(2);
		System.out.println(executor.getClass().getName());
		
		List<Future<String>> listOfResults = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			var process = new Process();
			listOfResults.add(executor.submit(process));
		}

		for (var result : listOfResults) {
			String answer = result.get(); // Waiting results here
			System.out.println(answer);
		}
		executor.shutdown();
	}

	private static void callableExample() {
		var executor = Executors.newSingleThreadExecutor();
		var submit = executor.submit(new Process());

		try {
			System.out.println("Before get");
			String result = submit.get();
			System.out.println(result);
			System.out.println("After get");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void runnableExample() throws InterruptedException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<?> submit = executor.submit(new SingleProcess());
		boolean done;

		do {
			done = submit.isDone();
			System.out.println(done);
			Thread.sleep(200);
		} while (!done);

		try {
			submit.get(); // Without get() we wouldn't catch exception
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		executor.shutdown();
	}
}

class SingleProcess implements Runnable {

	private String name = null;

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		System.out.println(name.length());
	}
}
