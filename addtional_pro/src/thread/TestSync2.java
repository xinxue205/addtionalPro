package thread;

public class TestSync2 implements Runnable{
	int b =100;
	
	public synchronized void m1() throws Exception{
		b = 1000;
		for (int i = 0; i < 100; i++) {
			int n = b+i;
			System.out.println("m1.b= "+ n);
		}
		Thread.sleep(3000);
	}
	
	public synchronized void m2() throws Exception{
		Thread.sleep(1500);
		b = 2000;
		System.out.println("m2.b= "+b);
	}
	
	public void run(){
		try {
			m1();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		TestSync2 ts2 = new TestSync2();
		Thread t = new Thread(ts2);
		t.start();
		ts2.m2();
		System.out.println("main.b="+ ts2.b);
	}
}
