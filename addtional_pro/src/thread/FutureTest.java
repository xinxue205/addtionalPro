package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Callable+Future/FutureTask却可以获取多线程运行的结果，可以在等待时间太长没获取到需要的数据的情况下取消该线程的任务
 * @author Administrator
 *
 */
public class FutureTest {
	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		final ExecutorService exec = Executors.newFixedThreadPool(5);
		Callable<String> call = new Callable<String>() {
			public String call() throws Exception {
				System.out.println("call begin execute");
				Thread.sleep(1000 * 3);//休眠指定的时间，此处表示该操作比较耗时
				return "Other less important but longtime things.";
			}
		};
		Future<String> task = exec.submit(call);//提交给执行器执行
		//重要的事情
		Thread.sleep(1000 * 3);
		System.out.println("Let's do important things.");
		//不重要的事情
		String obj = task.get();
		System.out.println(obj);
		//关闭线程池
		exec.shutdown();
	}
}