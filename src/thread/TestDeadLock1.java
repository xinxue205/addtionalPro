package thread;

public class TestDeadLock1 implements Runnable {
	Object ob1 = new Object();
	Object ob2 = new Object();
	private int a;
	public void run() {
		if (a == 0) {
			synchronized (ob1) {
				try {
					// Thread.sleep(2000);//放输出后面，不然等2秒结束，a已经=1了
					System.out.println("this is ob1" + " a=" + a);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (ob2) {
					System.out.println("ob2 in the ob1" + " a=" + a);
				}
			}
		}
		if (a == 1) {synchronized (ob2) {
			try {
				// Thread.sleep(10);
				System.out.println("this is ob2" + " a=" + a);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (ob1) {
				System.out.println("ob1 in the ob2" + " a=" + a);
				}
			}
		}
		}// 下面是主函数
	public static void main(String[] args) {
		TestDeadLock1 td = new TestDeadLock1();
		// ThreadDeadLock td2 = new ThreadDeadLock();
		td.a = 0;
		new Thread(td).start();
		try {
			Thread.sleep(1000);
		//主函数不sleep会直接运行到结束，a已经=1了
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		td.a = 1;
		new Thread(td).start();// new Thread(td2).start();}}
	}

}