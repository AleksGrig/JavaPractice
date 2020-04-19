package concurrency.miscellaneous;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Use method take() of CompletionService - takes first completed task 
// or waits if none is ready
@SuppressWarnings({"unused", "unchecked"})
public class ExecutorReturnFirstReady {

	private static class SumTask implements Callable<TaskResult<String, Integer>> {

		private static int count;
		private String id = "Task-" + ++count;
		private int a;
		private int b;
		private long sleepTime;

		private SumTask(int a, int b, long sleepTime) {
			this.a = a;
			this.b = b;
			this.sleepTime = sleepTime;
		}

		@Override
		public TaskResult<String, Integer> call() throws Exception {
			System.out.println(Thread.currentThread().getName() + ": " + id + " STARTS");
			Thread.sleep(sleepTime);
			TaskResult<String, Integer> result = new TaskResult<>(id, a + b);
			System.out.println(Thread.currentThread().getName() + ": " + id + " ENDS");
			return result;
		}
	}

	private static class TaskResult<S, R> {
		private S taskId;
		private R taskResult;

		private TaskResult(S taskId, R taskResult) {
			this.taskId = taskId;
			this.taskResult = taskResult;
		}

		public S getTaskId() {
			return taskId;
		}

		public R getTaskResult() {
			return taskResult;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
			result = prime * result + ((taskResult == null) ? 0 : taskResult.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TaskResult<String, Integer> other = (TaskResult<String, Integer>) obj;
			if (taskId == null) {
				if (other.taskId != null)
					return false;
			} else if (!taskId.equals(other.taskId))
				return false;
			if (taskResult == null) {
				if (other.taskResult != null)
					return false;
			} else if (!taskResult.equals(other.taskResult))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "TaskResult [taskId=" + taskId + ", taskResult=" + taskResult + "]";
		}		
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Main thread starts");
		ExecutorService service = Executors.newCachedThreadPool();
		CompletionService<TaskResult<String, Integer>> tasks = new ExecutorCompletionService<>(service);
		for (int i = 0; i < 3; i++) {
			tasks.submit(new SumTask(i, i + 1, (long) (1000 * (Math.random() * 3 + 1))));
		}
		service.shutdown();

		for (int i = 0; i < 3; i++) {
			System.out.println("Result is " + tasks.take().get());
		}
		System.out.println("Main thread ends");
	}
}
