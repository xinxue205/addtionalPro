package thread;

/**
 * Obj.wait()����Obj.notify()����Ҫ��synchronized(Obj)һ��ʹ�ã�Ҳ����wait,��notify������Ѿ���ȡ��Obj�����в��������﷨�Ƕ���˵����Obj.wait(),Obj.notify������synchronized(Obj){...}�����ڡ�
   * �ӹ�������˵wait����˵�߳��ڻ�ȡ�������������ͷŶ�������ͬһʱ���߳����ߡ�ֱ���������̵߳��ö����notify()���Ѹ��̣߳��ż�����ȡ�����������������С���Ӧ��notify()���ǶԶ������Ļ��Ѳ�����<br>
   * sleep() ����û���ͷ������� wait() �����ͷ�������<br>
���߶�������ͣ���̣߳�<br>
wait() ͨ���������̼߳佻��/ͨ�ţ�sleep() ͨ����������ִͣ�У�<br>
wait() ���������ú��̲߳����Զ����ѣ���Ҫ����̵߳���ͬһ�������ϵ� notify() ���� notifyAll() ������sleep ִ����󣬻��Զ����ѡ�<br>
 * @author Administrator
 *
 */
public class TestWait implements Runnable {   
	  
    private String name;   
    private Object prev;   
    private Object self;   
  
    private TestWait(String name, Object prev, Object self) {   
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
        TestWait pa = new TestWait("A", c, a);   
        TestWait pb = new TestWait("B", a, b);   
        TestWait pc = new TestWait("C", b, c);   
           
           
        new Thread(pa).start();
        new Thread(pb).start();
        new Thread(pc).start();    }   
} 
