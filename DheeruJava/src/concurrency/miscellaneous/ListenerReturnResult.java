package concurrency.miscellaneous;

import java.util.ArrayList;
import java.util.List;

public class ListenerReturnResult {

	private interface ResultListener<T> {
		void notyfyResult(T result, String id);
		void printResult();
	}

	private static class SumListener implements ResultListener<Integer> {

		private Integer result;
		private String id;
		private volatile boolean ready = false;

		@Override
		public void notyfyResult(Integer result, String id) {
			this.result = result;
			this.id = id;
			ready = true;
		}

		@Override
		public void printResult() {
			while(!ready);
			System.out.println(id + " result: " + result);
		}
	}

	private static class SumTask implements Runnable {

		private static int count;
		public String id = "Task-" + ++count;
		ResultListener<Integer> listener;
		private int a;
		private int b;
		private long sleepTime;
		private int result;

		private SumTask(int a, int b, long sleepTime, ResultListener<Integer> listener) {
			this.a = a;
			this.b = b;
			this.sleepTime = sleepTime;
			this.listener = listener;
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
			listener.notyfyResult(result, id);

			System.out.println(Thread.currentThread().getName() + ": " + id + " ENDS");
		}
	}

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName() + " STARTS");

		List<Runnable> tasks = new ArrayList<>();
		List<ResultListener<Integer>> listeners = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) {
			listeners.add(new SumListener());
		}
		for (int i = 0; i < 3; i++) {
			tasks.add(new SumTask(i, i + 1, 1000 * (i + 1), listeners.get(i)));
		}
		for (Runnable task : tasks) {
			new Thread(task).start();
		}
		
		for(var listener : listeners) {
			listener.printResult();
		}
		
		System.out.println(Thread.currentThread().getName() + " ENDS");
	}

}
