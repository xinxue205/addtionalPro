package thread;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {
	public static void main(String[] args) throws Exception {
		//ÿ��������һ���߳�
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(11);
		scheduler.scheduleWithFixedDelay(new Runnable() {//���ִ�У�ÿ�������̵߳ĵ���ʱ�����ִ��Ӱ�죨ִ��ʱ���������ʱ�����´ε��Ȳ�ִ�У�
//		scheduler.scheduleWithFixedDelay(new Runnable() {//ÿ��ִ������ӳ�ִ��
			public void run(){
				System.out.println(new Date()+" 111111---"+Thread.currentThread());
				try {
					Thread.sleep(5*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 0, 3, TimeUnit.SECONDS);
		
//		Thread.sleep(1*1000);
//		
		scheduler.scheduleWithFixedDelay(new Runnable() {
			public void run(){
				System.out.println(new Date()+" 222222---"+Thread.currentThread());
				try {
					Thread.sleep(4*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 0, 3, TimeUnit.SECONDS);
	}
}
