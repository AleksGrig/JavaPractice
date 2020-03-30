package concurrency.synchronization.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

public class ThreadSynchExample {
	public static void main(String[] args) {
		Runnable barrier1Action = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					System.out.println("BarrierAction 1 executed");
				} catch (Exception ex) {
				}
			}
		};
		Runnable barrier2Action = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					System.out.println("BarrierAction 2 executed");
				} catch (Exception ex) {
				}
			}
		};

//        CyclicBarrier barrier1 = new CyclicBarrier(3);
//        CyclicBarrier barrier2 = new CyclicBarrier(3);
		CyclicBarrier barrier1 = new CyclicBarrier(3, barrier1Action);
		CyclicBarrier barrier2 = new CyclicBarrier(3, barrier2Action);

		CyclicBarrierRunnable barrierRunnable1 = new CyclicBarrierRunnable(barrier1, barrier2);
		CyclicBarrierRunnable barrierRunnable2 = new CyclicBarrierRunnable(barrier1, barrier2);
		CyclicBarrierRunnable barrierRunnable3 = new CyclicBarrierRunnable(barrier1, barrier2);

		new Thread(barrierRunnable1).start();
		new Thread(barrierRunnable2).start();
		new Thread(barrierRunnable3).start();
	}
}

