package thread;

import java.util.Scanner;

/**
 * 对于一个死循环式的线程是守护线程，它会受其它用户线程影响，当其它用户线程（非守护线程）完成退出时，会关闭守护线程后退出jvm，而死循环式的非守护线程，只要无异常则会一直运行。
 * 守护线程一般用于后台执行一些定时清理的相关工作
 * @author Administrator
 *
 */
public class DaemonThreadTest implements Runnable {
    public void run() {
        while (true) {
        	System.out.println("i am alive");
        	try {
        		Thread.sleep(1000);
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
//            for (int i = 1; i <= 100; i++) {
//
//            }
        }
    }

    public static void main(String[] args) throws Exception {
        Thread daemonThread = new Thread(new DaemonThreadTest());
        // 设置为守护进程
        daemonThread.setDaemon(true);
        daemonThread.start();
        System.out.println("isDaemon = " + daemonThread.isDaemon());
        Thread.sleep(5555);
//        Scanner scanner = new Scanner(System.in);
//        // 接受输入，使程序在此停顿，一旦接受到用户输入,main线程结束，JVM退出!
//        scanner.next();  
        //AddShutdownHook方法增加JVM停止时要做处理事件：
        //当JVM退出时，打印JVM Exit语句.
        Runtime.getRuntime().addShutdownHook(new Thread(){
              @Override
            public void run() {
                // TODO Auto-generated method stub
            System.out.println("JVM Exit!");
            }
        });
    }
}
