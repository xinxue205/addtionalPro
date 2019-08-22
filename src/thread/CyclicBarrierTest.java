package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier: N个线程相互等待，任何一个线程完成之前，所有的线程都必须等待。
 *           类似跑步比赛准备，当运动员都准备好后，才喊预备开始，只要有一个人没有准备好，大家都等待.
 * @author Administrator
 *
 */
public class CyclicBarrierTest {

    private ExecutorService executorService;
    private CyclicBarrier cyclicBarrier;
    private int parties;

    public CyclicBarrierTest(int parties) {
        executorService = Executors.newFixedThreadPool(parties);
        cyclicBarrier = new CyclicBarrier(parties, () -> System.out.println(Thread.currentThread().getName() + " gets barrierCommand done"));
        this.parties = parties;
    }

    public static void main(String[] args) {
    	CyclicBarrierTest cyclicBarrierExample = new CyclicBarrierTest(10);
        cyclicBarrierExample.example();
    }

    public void example() {
        for (int i = 0; i < parties; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(4000);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " gets job done");
            });
        }
        executorService.shutdown();
    }
}
