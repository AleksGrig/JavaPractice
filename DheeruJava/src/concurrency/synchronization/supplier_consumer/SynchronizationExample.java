package concurrency.synchronization.supplier_consumer;

public class SynchronizationExample {

	public static void main(String[] args) {
		var resource = new Resource();
		var producer = new Producer(resource);
		var consumer = new Consumer(resource);

		producer.start();
		consumer.start();
	}

}
