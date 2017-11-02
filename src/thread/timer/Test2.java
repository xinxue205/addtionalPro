package thread.timer;

import java.util.Timer;
import java.util.TimerTask;

/* 
 * @author wuxinxue
 * @copyRight sinobest
 * @time 2017-2-17 ÏÂÎç4:28:23
 */
public class Test2 {
	public static void main(String[] args) throws Exception {
        System.out.println("-------------------"+ Thread.currentThread().getName() + ":" + System.currentTimeMillis());
		Timer timer = new Timer("timer1");
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
		
		Timer timer2 = new Timer("timer2");
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
		timer2.schedule( timerTask2, 0, 2 * 1000 );

//		Thread.sleep(5*1000);
//		timer.cancel();
        System.out.println("-------------------"+ Thread.currentThread().getName() + ":" + System.currentTimeMillis());
	}
}
