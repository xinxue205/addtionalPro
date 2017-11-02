package thread.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.hssf.util.HSSFColor.AUTOMATIC;

/* timer������һ�����ȣ���������һ������󣬵ȴ���������ɺ���������һ���Ѿ���ʱ�ĸ����������񣬸��������ǰ���������������� ÿһ��������´�����ʱ����ϴ�����ʱ�����úã����������ʱ���ʱ��ǰ������û��ɾͲ���������ʱ���������Ҫ�ȸ�������ɺ���������������
 * @author wuxinxue
 * @copyRight sinobest
 * @time 2017-2-17 ����4:28:23
 */
public class Test {
	
	/**
	 * 2��scheduler����Ӱ��
	 * @param args
	 */
	public static void main(String[] args) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		final AtomicInteger i = new AtomicInteger(0);
		 scheduler.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				System.out.println(i.getAndIncrement());
				if (i.get()  ==4 ) throw new RuntimeException(" error occur");
			}
			 
		 }, 1, 1, TimeUnit.SECONDS);
		ScheduledExecutorService scheduler1 = Executors.newSingleThreadScheduledExecutor();
		scheduler1.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				System.out.println(2);
			}
			 
		 }, 1, 1, TimeUnit.SECONDS);
	}
	public static void main1(String[] args) throws Exception {
        System.out.println("-------------------"+ Thread.currentThread().getName() + ":" + System.currentTimeMillis());
		Timer timer = new Timer("timer");
		TimerTask timerTask = new TimerTask() {
			public void run() {
				try {
					Thread.sleep(1*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          System.out.println("-------------------task1,"+ Thread.currentThread().getName() + ":" + System.currentTimeMillis());
	    	}
		};
		timer.schedule( timerTask, 0, 4 * 1000 );
		
		TimerTask timerTask2 = new TimerTask() {
			public void run() {
				try {
					Thread.sleep(3*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          System.out.println("-------------------task2,"+ Thread.currentThread().getName() + ":" + System.currentTimeMillis());
	    	}
		};
		timer.schedule( timerTask2, 0, 2 * 1000 );

//		Thread.sleep(5*1000);
//		timer.cancel();
        System.out.println("-------------------"+ Thread.currentThread().getName() + ":" + System.currentTimeMillis());
	}
}
