package thread;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {
	public static void main(String[] args) throws Exception {
		//每个任务开启一个线程
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(11);
		scheduler.scheduleWithFixedDelay(new Runnable() {//间隔执行，每个任务线程的调度时间会受执行影响（执行时长超过间隔时长，下次调度不执行）
//		scheduler.scheduleWithFixedDelay(new Runnable() {//每次执行完后延迟执行
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
