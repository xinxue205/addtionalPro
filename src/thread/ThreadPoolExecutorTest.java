package thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
	public static void main(String[] args) {
		SynchronousQueue<Runnable> executorQueue = new SynchronousQueue<Runnable>();
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 15; i++) {
			executor.execute(new Runnable() {
				public void run() {
					try {
						Thread.sleep(20000);
						System.out.println("thread pool Thread finish---------------");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		System.out.println("start thread succ!!!");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(){
			public void run() {
				System.out.println("test Thread---------------");
			};
		}.start();
	}
	
	public static void main1(String[] args) {
		ThreadPoolExecutor es = new ThreadPoolExecutor(2, 3, 30000L, TimeUnit.MILLISECONDS,  new LinkedBlockingQueue<Runnable>(4)); 
		for (int i = 0; i < 5; i++) {
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
		
		System.out.println(222);
		new Thread(){
			public void run() {
				System.out.println(111);
			};
		}.start();
		
		for (int i = 0; i < 15; i++) {
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
		
	}
}
