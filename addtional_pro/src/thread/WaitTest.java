package thread;

/**
 * Obj.wait()����Obj.notify()����Ҫ��synchronized(Obj)һ��ʹ�ã�Ҳ����wait,��notify������Ѿ���ȡ��Obj�����в��������﷨�Ƕ���˵����Obj.wait(),Obj.notify������synchronized(Obj){...}�����ڡ�
   * �ӹ�������˵wait����˵�߳��ڻ�ȡ�������������ͷŶ�������ͬһʱ���߳����ߡ�ֱ���������̵߳��ö����notify()���Ѹ��̣߳��Ÿɼ�����ȡ�����������������С���Ӧ��notify()���ǶԶ������Ļ��Ѳ�����
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
                    
                    self.notify();  //ע�⣺notify()���ú󣬲����������ͷŶ������ģ������ڶ�Ӧ��synchronized(){}�������н������Լ������ͷ�����JVM����wait()���������߳������ѡȡһ�̣߳�������������������̣߳��������� 
                    System.out.println("��û���ͷ���");
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
