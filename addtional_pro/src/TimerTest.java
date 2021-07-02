import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	public static void main(String[] args) {
		final Timer timer = new Timer("timer-test");
		TimerTask timerTask = new TimerTask() {
			public void run() {
	          System.out.println("-------------test timer!");
	        }
		};
      timer.schedule( timerTask, 5 * 1000, 5 * 1000 );
      
      System.out.println("main end");
	}
}
