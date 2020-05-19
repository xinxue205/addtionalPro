package thread;

import java.util.Date;

public class TestInterrupt {
	public static void main(String[] args) {
		MyThread mt = new MyThread();
		mt.start();
		try { 
			Thread.sleep(10000);
		} catch (InterruptedException e){}
		mt.flag = false;
	}
}

class MyThread extends Thread{
	boolean flag = true;
	public void run() {
		while (flag){
		System.out.println("==="+new Date()+"====");
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO: handle exception
				
			}
		}
	}
}