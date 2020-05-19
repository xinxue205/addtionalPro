package thread;

/**
 * 同时跑2个线程执行打印0-32
 * 
 * */
public class TestThread1 {
	public static void main(String[] args) {
		
		for (int i = 0; i < 6; i++) {
			Runner1 r =new Runner1();
			Thread t = new Thread(r);
			t.start();
		}
	}
}

class Runner1 implements Runnable {
	public void run() {
		try {
			Thread.sleep(22222);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < 11; i++) {
			System.out.println("***Runner1: "+ i);
		}	
	}
	
}