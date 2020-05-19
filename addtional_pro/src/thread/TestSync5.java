package thread;

public class TestSync5 extends Thread{
	public void run(){
		synchronized (this) {
			for (int i = 0; i < 3; i++) {
				System.out.println(Thread.currentThread().getName()+" synchronized loop " + i);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			System.out.println("Current Thread finished!!!");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	void share(){
		System.out.println("Sharethread try to open the synchronize lock!");
	}
	
	public static void main(String[] args) {
		//TestSync4 test1 = new TestSync4();
		//TestSync4 test2 = new TestSync4();
		TestSync5 test = new TestSync5();
		Thread test1 = new Thread(test);
		test1.start();
		test.share();
	}
	
}