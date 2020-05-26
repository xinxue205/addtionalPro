package thread;

/**
 * 同时跑2个线程执行打印0-50<br>
 * JDK1.6 引入了自适应自旋锁;利用 CAS，当第一个线程 A 获取锁的时候，能够成功获取到，不会进入 while 循环，如果此时线程 A 没有释放锁，另一个线程 B 又来获取锁，此时由于不满足 CAS，所以就会进入 while 循环，不断判断是否满足 CAS，直到A线程调用 unlock 方法释放了该锁。<br>
 * CAS:compare and swap (比较与替换)，是一种有名的无锁算法;当且仅当 V的值等于 A时，CAS通过原子方式用新值B来更新V的值，否则不会执行任何操作（比较和替换是一个原子操作）。它是一个不断重试的自旋操作。<br>
 * <br>
 * 偏向锁:锁对象第一次被线程获得的时候，进入偏向状态，,记录线程id到mark word中，该线程以后再进入这个锁相关的同步块就不需要再进行任何同步操作；直到有另外一个线程去尝试获取这个锁对象，会修改偏向状态到新线程。<br>
 * 偏向锁和轻量级锁的目的很像，都是为了没有多线程竞争的前提下，减少传统的重量级锁使用操作系统互斥量产生的性能消耗。而轻量级锁在无竞争的情况下使用 CAS 操作去代替使用互斥量。偏向锁则在无竞争的情况下会把整个同步都消除掉。
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
