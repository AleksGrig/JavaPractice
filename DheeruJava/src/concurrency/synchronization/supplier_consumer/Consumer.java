package concurrency.synchronization.supplier_consumer;

public class Consumer extends Thread {

	private Resource resource;

	public Consumer(Resource resource) {
		this.resource = resource;
	}

	@Override
	public void run() {
		for (int i = 0; i < Resource.COUNT; i++) {
			resource.get();
		}
	}
}
