package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Callable+Future/FutureTaskȴ���Ի�ȡ���߳����еĽ���������ڵȴ�ʱ��̫��û��ȡ����Ҫ�����ݵ������ȡ�����̵߳�����
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
				Thread.sleep(1000 * 3);//����ָ����ʱ�䣬�˴���ʾ�ò����ȽϺ�ʱ
				return "Other less important but longtime things.";
			}
		};
		Future<String> task = exec.submit(call);//�ύ��ִ����ִ��
		//��Ҫ������
		Thread.sleep(1000 * 3);
		System.out.println("Let's do important things.");
		//����Ҫ������
		String obj = task.get();
		System.out.println(obj);
		//�ر��̳߳�
		exec.shutdown();
	}
}