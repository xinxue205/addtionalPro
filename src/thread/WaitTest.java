package thread;

/**
 * Obj.wait()，与Obj.notify()必需要与synchronized(Obj)一起使用，也就是wait,与notify是针对已经获取了Obj锁进行操作，从语法角度来说就是Obj.wait(),Obj.notify必须在synchronized(Obj){...}语句块内。
   * 从功能上来说wait就是说线程在获取对象锁后，主动释放对象锁，同一时候本线程休眠。直到有其他线程调用对象的notify()唤醒该线程，才干继续获取对象锁，并继续运行。对应的notify()就是对对象锁的唤醒操作。
 * @author Administrator
 *
 */
public class WaitTest implements Runnable {   
	  
    private String name;   
    private Object prev;   
    private Object self;   
  
    private WaitTest(String name, Object prev, Object self) {   
        this.name = name;   
        this.prev = prev;   
        this.self = self;   
    }   
  
    @Override  
    public void run() {   
        int count = 10;   
        while (count > 0) {   
            synchronized (prev) {   
                synchronized (self) {   
                    System.out.print(name);   
                    count--;  
                    
                    self.notify();  //注意：notify()调用后，并非立即就释放对象锁的，而是在对应的synchronized(){}语句块运行结束，自己主动释放锁后，JVM会在wait()对象锁的线程中随机选取一线程，赋予其对象锁，唤醒线程，继续运行 
                    System.out.println("还没有释放锁");
                }   
                try {   
                    prev.wait();   
                } catch (InterruptedException e) {   
                    e.printStackTrace();   
                }   
            }   
  
        }   
    }   
  
    public static void main(String[] args) throws Exception {   
        Object a = new Object();   
        Object b = new Object();   
        Object c = new Object();   
        WaitTest pa = new WaitTest("A", c, a);   
        WaitTest pb = new WaitTest("B", a, b);   
        WaitTest pc = new WaitTest("C", b, c);   
           
           
        new Thread(pa).start();
        new Thread(pb).start();
        new Thread(pc).start();    }   
} 
