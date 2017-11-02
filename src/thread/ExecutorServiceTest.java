package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceTest {
	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(10); 
		for (int i = 0; i < 22; i++) {
			es.execute(new Thread(){
				@Override
				public void run() {
					try {
						System.out.println(123);
						sleep(20*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		
		new Thread(){
			public void run() {
				System.out.println(111);
			};
		}.start();
		
		es.shutdown();  
	}
}
