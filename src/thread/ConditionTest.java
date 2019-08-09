package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    public static void main(String[] args) throws InterruptedException {

        lock.lock();
        new Thread(new SignalThread()).start();
        System.out.println("���̵߳ȴ�֪ͨ");
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
        System.out.println("���ָ̻߳�����");
    }
    static class SignalThread implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                condition.signal();
                System.out.println("���߳�֪ͨ");
            } finally {
                lock.unlock();
            }
        }
    }
}
