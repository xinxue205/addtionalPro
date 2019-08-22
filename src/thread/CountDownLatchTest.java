package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch : 一个线程(或者多个)， 等待另外N个线程完成某个事情之后才能执行。
 * 类似跑步比赛中，等所有运动员跑完了，裁判才喊结束。
 * @author Administrator
 *
 */
public class CountDownLatchTest {

    private ExecutorService executorService;
    private CountDownLatch countDownLatch;
    private int parties;

    public static void main(String[] args){
    	CountDownLatchTest countDownLatchExample = new CountDownLatchTest(10);
        try {
            countDownLatchExample.example();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CountDownLatchTest(int parties) {
        executorService = Executors.newFixedThreadPool(parties);
        countDownLatch = new CountDownLatch(parties);
        this.parties = parties;
    }

    public void example() throws InterruptedException {
        for (int i = 0; i < parties; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(11000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " gets job done");
                countDownLatch.countDown(); // 线程完成任务之后countDown
            });
        }
        // 等待所有的任务完成
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " reach barrier");
        executorService.shutdown();
    }
}
