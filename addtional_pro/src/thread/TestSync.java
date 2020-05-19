package thread;

/**
 * 同时跑2个线程执行打印0-50
 * 
 * */
public class TestSync implements Runnable{
	Timer timer = new Timer();
	public static void main(String[] args) {
		TestSync test = new TestSync();
		Thread t1 = new Thread(test);
		Thread t2 = new Thread(test);
		t1.setName("T1");
		t2.setName("T2");
		t1.start();
		t2.start();
	}
	public void run(){
		timer.add(Thread.currentThread().getName());
	}
}

class Timer{
	private static int num = 0;
	public synchronized void add(String name){
		//synchronized(this){
		System.out.println(name+ " is using this thread.");
		num++;
		try {Thread.sleep(3000);}
		catch (InterruptedException e) {}
			System.out.println(name+ ", you are the "+num+"th user of this thread.");
			// TODO: handle exception
		//}	
	}
}
