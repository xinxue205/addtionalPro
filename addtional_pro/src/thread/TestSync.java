package thread;

/**
 * ͬʱ��2���߳�ִ�д�ӡ0-50<br>
 * JDK1.6 ����������Ӧ������;���� CAS������һ���߳� A ��ȡ����ʱ���ܹ��ɹ���ȡ����������� while ѭ���������ʱ�߳� A û���ͷ�������һ���߳� B ������ȡ������ʱ���ڲ����� CAS�����Ծͻ���� while ѭ���������ж��Ƿ����� CAS��ֱ��A�̵߳��� unlock �����ͷ��˸�����<br>
 * CAS:compare and swap (�Ƚ����滻)����һ�������������㷨;���ҽ��� V��ֵ���� Aʱ��CASͨ��ԭ�ӷ�ʽ����ֵB������V��ֵ�����򲻻�ִ���κβ������ȽϺ��滻��һ��ԭ�Ӳ�����������һ���������Ե�����������<br>
 * <br>
 * ƫ����:�������һ�α��̻߳�õ�ʱ�򣬽���ƫ��״̬��,��¼�߳�id��mark word�У����߳��Ժ��ٽ����������ص�ͬ����Ͳ���Ҫ�ٽ����κ�ͬ��������ֱ��������һ���߳�ȥ���Ի�ȡ��������󣬻��޸�ƫ��״̬�����̡߳�<br>
 * ƫ����������������Ŀ�ĺ��񣬶���Ϊ��û�ж��߳̾�����ǰ���£����ٴ�ͳ����������ʹ�ò���ϵͳ�������������������ġ��������������޾����������ʹ�� CAS ����ȥ����ʹ�û�������ƫ���������޾���������»������ͬ������������
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
