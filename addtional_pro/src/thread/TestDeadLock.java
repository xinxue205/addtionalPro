package thread;

/**
 * ������������<br>
 * ���������� ����Դ����һ��ʱ��ֻ��һ���߳�ռ�ã�<br>
�����뱣��������һ���߳���������Դ�����������ѻ�õ���Դ���ֲ��ţ�<br>
�������������߳��Ѿ���õ���Դ��δʹ����֮ǰ���ܱ������߳�ǿ�а��ᣬֻ���Լ�ʹ����Ϻ���ͷ���Դ��<br>
ѭ���ȴ������������߳�֮���γ�һ��ͷβ��ӵ�ѭ���ȴ���Դ��ϵ��<br>
<br>
�ƻ����������������������û�а취�ƻ�����Ϊ����������������������ǻ���ģ��ٽ���Դ��Ҫ������ʣ���<br>
�ƻ������뱣��������һ�����������е���Դ<br>
�ƻ�������������ռ�ò�����Դ���߳̽�һ������������Դʱ��������벻�������������ͷ���ռ�е���Դ��<br>
�ƻ�ѭ���ȴ�����������˳��������Դ��Ԥ��������ĳһ˳��������Դ���ͷ���Դ�����ͷš��ƻ�ѭ���ȴ�������<br>
 * @author Administrator
 *
 */
public class TestDeadLock implements Runnable{
	public int flag = 1;
	static Object o1 = new Object(), o2 = new Object();
	public void run() {
		System.out.println("flag: " + flag);
		if (flag == 1) {
			synchronized (o1){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (o2) {
					System.out.println("1");
				}
			}
		}
		
		if (flag == 0){
//			try {
//				Thread.currentThread().sleep(2100);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			synchronized (o2) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (o1) {
					System.out.println("0");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		TestDeadLock td1 = new TestDeadLock();
		TestDeadLock td2 = new TestDeadLock();
		td1.flag = 1;
		td2.flag = 0;
		Thread t1 = new Thread(td1);
		Thread t2 = new Thread(td2);
		t1.start();
		t2.start();
	}
}
