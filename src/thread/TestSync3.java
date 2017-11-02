package thread;

public class TestSync3 extends Thread{
	Book book = new Book();
	public static void main(String[] args) throws InterruptedException {
		//TestSync3 test1 = new TestSync3();
		//TestSync3 test2 = new TestSync3();
		TestSync3 test = new TestSync3();
		Thread test1 = new Thread(test);
		Thread test2 = new Thread(test);
		test1.start();
		test2.start();
	}
	public void run(){
		try {
			book.read();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Book{
	private static int times=0;
	public synchronized void read() throws InterruptedException {
		times++;
		Thread.sleep(1000);
		System.out.println(times+" is reading the book");
		Thread.sleep(1000);
		System.out.print(times+" is reading the book");
		Thread.sleep(1000);
		System.out.println("  Finished the "+times+" time read");
	}
}


