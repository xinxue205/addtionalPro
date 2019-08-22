package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier: N���߳��໥�ȴ����κ�һ���߳����֮ǰ�����е��̶߳�����ȴ���
 *           �����ܲ�����׼�������˶�Ա��׼���ú󣬲ź�Ԥ����ʼ��ֻҪ��һ����û��׼���ã���Ҷ��ȴ�.
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
