package thread;

import java.util.Scanner;

public class TestSync7 implements Runnable{
	Money mo = new Money();
	public static void main(String[] args) {
		TestSync7 test = new TestSync7();
		Thread th = new Thread(test);
		Thread th1 = new Thread(test);
		th.start();
		th1.start();
	}
	public void run() {
		mo.get();
	}
}

class Money{
	void get(){
		synchronized (this){
			int i =5000;
			int sum = 0;
			Scanner sc = new Scanner(System.in);
			System.out.println("Pls enter how many");
			int a = sc.nextInt();
			sum = i-a;
			System.out.println("Óà¶îÎª£º"+sum);
		}
	}
}