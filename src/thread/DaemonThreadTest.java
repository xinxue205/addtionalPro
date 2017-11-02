package thread;

import java.util.Scanner;

public class DaemonThreadTest implements Runnable {
    public void run() {
        while (true) {
            for (int i = 1; i <= 100; i++) {

                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread daemonThread = new Thread(new DaemonThreadTest());
        // ����Ϊ�ػ�����
        daemonThread.setDaemon(true);
        daemonThread.start();
        System.out.println("isDaemon = " + daemonThread.isDaemon());
        Scanner scanner = new Scanner(System.in);
        // �������룬ʹ�����ڴ�ͣ�٣�һ�����ܵ��û�����,main�߳̽�����JVM�˳�!
        scanner.next();  
        //AddShutdownHook��������JVMֹͣʱҪ�������¼���
        //��JVM�˳�ʱ����ӡJVM Exit���.
        Runtime.getRuntime().addShutdownHook(new Thread(){
              @Override
            public void run() {
                // TODO Auto-generated method stub
            System.out.println("JVM Exit!");
            }
          });
    }
}
