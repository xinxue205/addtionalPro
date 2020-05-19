package multi.state;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ConcurrentThread {
	 public static void main(String[] args) {  
	        ExecutorService exec=Executors.newCachedThreadPool();  
	          
	        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {

				@Override
				public String call() throws Exception {
					return Thread.currentThread().getName();
				}
			});  
	              
	            try {  
	            	long start = System.currentTimeMillis();
	            	exec.execute(task);//FutureTask实际上也是一个线程  
	                String result=task.get();//取得异步计算的结果，如果没有返回，就会一直阻塞等待  
	                System.out.printf((System.currentTimeMillis()- start )+"-----get:%s%n", result);  
	            } catch (InterruptedException e) {  
	                e.printStackTrace();  
	            } catch (ExecutionException e) {  
	                e.printStackTrace();  
	 		}
	    } 
}
