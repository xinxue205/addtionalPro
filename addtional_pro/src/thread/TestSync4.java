package thread;

public class TestSync4 extends Thread{
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
	
	public static void main(String[] args) {
		//TestSync4 test1 = new TestSync4();
		//TestSync4 test2 = new TestSync4();
		TestSync4 test = new TestSync4();
		Thread test1 = new Thread(test);
		Thread test2 = new Thread(test);
		test.start();
		test1.start();
		test2.start();
	}
	
}
