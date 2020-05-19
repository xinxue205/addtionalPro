/**
 * 
 */
package thread;

/**
 * @author Administrator
 * @date 2014-11-28 ÉÏÎç11:35:48
 * @version 1.0  Administrator create
 * @CopyRight (c) 2014 xxxx¹«Ë¾ 
 */
public class StartThreadEnd extends Thread{

	
	public static void main(String[] args) {
		Object obj = new Object();
		TT t = new TT(obj);
		t.start();
		
		t = new TT(obj);
		t.start();
		
		t = new TT(obj);
		t.start();
	}
	
}

class TT extends Thread{
	Object o;
	
	TT(Object o){
		this.o = o;
	}
	@Override
	public void run() {
		while (true){
//			synchronized (o) {
//				System.out.println(Thread.currentThread().getName()+":111");
//				System.out.println(Thread.currentThread().getName()+":222");
//			}
				mRun();
		}
	}
	
	public static synchronized void mRun(){
		System.out.println(Thread.currentThread().getName()+":111");
		System.out.println(Thread.currentThread().getName()+":222");
	}
}
