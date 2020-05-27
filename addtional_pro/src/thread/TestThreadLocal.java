package thread;

import java.util.HashMap;

/**
 *ÿ���߳� Thread �ڲ�����һ�� ThreadLocal.ThreadLcoalMap ���͵ĳ�Ա���� threadLocals(������map0)��<br>
 * ThreadLocalMap �� ThreadLocal ���ڲ��࣬��� threadLocals ���������洢ʵ�ʵı��������ģ���ֵΪ��ǰ ThreadLocal ������value Ϊ��������(�� T ���͵ı���)��<br>
 * <br>
 * ����� ThreadLocal ʹ�ó���������������ݿ����ӡ�session ����ȡ�<br>
*���������е�һ�������漰��� DAO ����������Щ DAO �е� Connection ���ܴ����ӳ��л�ã�����Ǵ����ӳػ�õĻ������� DAO���ܻ��ò�ͬ�� Connection�������Ļ���û�а취���һ������ġ�<br>
*��DAO �е� Connection ����Ǵ� ThreadLocal �л�� Connection �Ļ�����ô��Щ DAO �ͻᱻ���뵽ͬһ�� Connection ֮�¡�
 * @author Administrator
 */
public class TestThreadLocal {

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
                map.put(i, i+id*100);//ThreadLocalc����
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
        TestThreadLocal test = new TestThreadLocal(); 
        test.run(); 
    }

}
