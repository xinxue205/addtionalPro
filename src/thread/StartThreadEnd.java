/**
 * 
 */
package thread;

/**
 * @author Administrator
 * @date 2014-11-28 ÉÏÎç11:35:48
 * @version 1.0  Administrator create
 * @CopyRight (c) 2014 xxxx¹«Ë¾ 
 */
public class StartThreadEnd extends Thread{
	@Override     
	public void run() {  
		for (int j = 0; j < 100; j++) {  
			try {  
				Thread.sleep(1000);
				System.out.println("sleep time: "+j);
			} catch (InterruptedException e) {
				e.printStackTrace();  
			}  
		}
	}
	
	public static void main(String[] args) {
		
	}
}
