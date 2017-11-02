package timer;

import java.util.Timer;  
import java.util.TimerTask;  
  
public class TimerTest  
{  
    private static long start;  
  
    public static void main(String[] args) throws Exception  
    {  
  
        TimerTask task1 = new TimerTask()  
        {  
            @Override  
            public void run()  
            {  
  
                System.out.println("task1 invoked ! "  
                        + System.currentTimeMillis() );  
                try  
                {  
                    Thread.sleep(3000);  
                } catch (InterruptedException e)  
                {  
                    e.printStackTrace();  
                }  
  
            }  
        };  
        TimerTask task2 = new TimerTask()  
        {  
            @Override  
            public void run()  
            {  
            	  System.out.println("task2 invoked ! "  
                          + System.currentTimeMillis() );  
            }  
        };  
        Timer timer = new Timer();  
        start = System.currentTimeMillis();  
        timer.schedule(task1, 1000, 2000);  
        Timer timer2 = new Timer();  
        timer2.schedule(task2,  1000, 2000);    
  
    }  
}  
