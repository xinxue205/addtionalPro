package thread;

/**
 * 死锁四条件：<br>
 * 互斥条件： 该资源任意一个时刻只由一个线程占用；<br>
请求与保持条件：一个线程因请求资源而阻塞，对已获得的资源保持不放；<br>
不剥夺条件：线程已经获得的资源在未使用完之前不能被其他线程强行剥夺，只由自己使用完毕后才释放资源；<br>
循环等待条件：若干线程之间形成一种头尾相接的循环等待资源关系。<br>
<br>
破坏互斥条件：这个条件我们没有办法破坏，因为我们用锁本身就是想让他们互斥的（临界资源需要互斥访问）。<br>
破坏请求与保持条件：一次性申请所有的资源<br>
破坏不剥夺条件：占用部分资源的线程进一步申请其他资源时，如果申请不到，可以主动释放它占有的资源。<br>
破坏循环等待条件：靠按顺序申请资源来预防。按照某一顺序申请资源，释放资源则反序释放。破坏循环等待条件。<br>
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
