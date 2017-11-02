package thread;

public class TestSync6 extends Thread{
	static Note note = new Note();
	public static void main(String[] args) throws InterruptedException {
		TestSync6 test = new TestSync6();
		Thread t1 = new Thread(test);
		t1.start();
		note.share();
	}
	public void run(){
		try {
			note.read();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Note{
	static synchronized void read() throws InterruptedException {
		String str = Thread.currentThread().getName();
		System.out.println(str+" are reading the note!");
		Thread.sleep(1000);
		System.out.println(str+" are reading the note!");
		Thread.sleep(1000);
		System.out.println(str+" finished the reading");
	}
	void share() throws InterruptedException{
		String str = Thread.currentThread().getName();
		System.out.println(str+" are shareing the note!");
		Thread.sleep(1000);
		System.out.println(str+" are shareing the note!");
		Thread.sleep(1000);
		System.out.println(str+" finished the shareing");
	}
}