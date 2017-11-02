package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorExceptionTest {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				double n = Math.random();
				test(n);	
			}

			private void test(double n) {
				if (n > 0) {
					throw new RuntimeException("error");
				}
			}
		});
	}
}
