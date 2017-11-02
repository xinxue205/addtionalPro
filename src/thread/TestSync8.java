package thread;

public class TestSync8 {
	public static void main(String[] args) throws Exception {
		final Tester t =  new Tester();
		new Thread(){
			public void run() {
				System.out.println(Thread.currentThread().getName()+" begin");
				synchronized (t) {
					try {
						Thread.sleep(2*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					t.say();
				}
			};
		}.start();
		
		synchronized (t) {
			Thread.sleep(1000);
			t.say();
		}
	}
}

class Tester{
	public void say() {
		System.out.println(Thread.currentThread().getName()+" yes");
	}
}