package threadlocal;

import java.util.HashMap;

/**
 *每个线程 Thread 内部都有一个 ThreadLocal.ThreadLcoalMap 类型的成员变量 threadLocals(下例的map0)，<br>
 * ThreadLocalMap 是 ThreadLocal 的内部类，这个 threadLocals 就是用来存储实际的变量副本的，键值为当前 ThreadLocal 变量，value 为变量副本(即 T 类型的变量)。<br>
 * <br>
 * 最常见的 ThreadLocal 使用场景是用来解决数据库连接、session 管理等。<br>
*由于请求中的一个事务涉及多个 DAO 操作，而这些 DAO 中的 Connection 不能从连接池中获得，如果是从连接池获得的话，两个 DAO可能会用不同的 Connection，这样的话是没有办法完成一个事务的。<br>
*而DAO 中的 Connection 如果是从 ThreadLocal 中获得 Connection 的话，那么这些 DAO 就会被纳入到同一个 Connection 之下。
 * @author Administrator
 */
public class ThreadLocalTest {

    static ThreadLocal<HashMap> map0 = new ThreadLocal<HashMap>(){
        @Override 
        protected HashMap initialValue() { 
            System.out.println(Thread.currentThread().getName()+"initialValue"); 
            return new HashMap(); 
        } 
    }; 
    
    
    public void run(){ 
        Thread[] runs = new Thread[3]; 
        for(int i=0;i<runs.length;i++){ 
            runs[i]=new Thread(new T1(i)); 
        } 
        for(int i=0;i<runs.length;i++){ 
            runs[i].start(); 
        } 
    } 
    
    public static class T1 implements Runnable{ 
        int id; 
        public T1(int id0){ 
            id = id0; 
        } 
        public void run() { 
            System.out.println(Thread.currentThread().getName()+":start"); 
            HashMap map = map0.get(); 
            for(int i=0;i<10;i++){ 
                map.put(i, i+id*100);//ThreadLocalc操作
                try{ 
                    Thread.sleep(100); 
                }catch(Exception ex){ 
                } 
            } 
            System.out.println(Thread.currentThread().getName()+':'+map); 
        } 
    } 
    /** 
     * Main 
     * @param args 
     */ 
    public static void main(String[] args){ 
        ThreadLocalTest test = new ThreadLocalTest(); 
        test.run(); 
    }

}
