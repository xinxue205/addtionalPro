package thread;

import java.util.Scanner;

/**
 * ����һ����ѭ��ʽ���߳����ػ��̣߳������������û��߳�Ӱ�죬�������û��̣߳����ػ��̣߳�����˳�ʱ����ر��ػ��̺߳��˳�jvm������ѭ��ʽ�ķ��ػ��̣߳�ֻҪ���쳣���һֱ���С�
 * �ػ��߳�һ�����ں�ִ̨��һЩ��ʱ�������ع���
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
        // ����Ϊ�ػ�����
        daemonThread.setDaemon(true);
        daemonThread.start();
        System.out.println("isDaemon = " + daemonThread.isDaemon());
        Thread.sleep(5555);
//        Scanner scanner = new Scanner(System.in);
//        // �������룬ʹ�����ڴ�ͣ�٣�һ�����ܵ��û�����,main�߳̽�����JVM�˳�!
//        scanner.next();  
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
