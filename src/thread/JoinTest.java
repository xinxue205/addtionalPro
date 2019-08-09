package thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * join()方法的作用，是等待这个线程结束；
 * 也就是说，t.join()方法阻塞调用此方法的线程(calling thread)进入 TIMED_WAITING 状态，直到线程t完成，此线程再继续；
 * 通常用于在main()主线程内，等待其它线程完成再结束main()主线程。
 * @author Administrator
 *
 */
public class JoinTest implements Runnable {
 
    private String name;
 
    public JoinTest(String name) {
    this.name = name;
    }
 
    public void run() {
    System.out.printf("%s begins: %s\n", name, new Date());
    try {
        TimeUnit.SECONDS.sleep(6);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.printf("%s has finished: %s\n", name, new Date());
    }
 
    public static void main(String[] args) {
    Thread thread1 = new Thread(new JoinTest("One"));
    Thread thread2 = new Thread(new JoinTest("Two"));
    thread1.start();
    thread2.start();
    
    try {
        thread1.join();
        thread2.join();
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
    System.out.println("Main thread is finished");
    }
 
}
