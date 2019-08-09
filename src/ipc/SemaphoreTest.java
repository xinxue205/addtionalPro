package ipc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

class ResourceManage {
    private final Semaphore semaphore ;
    private boolean resourceArray[];
    private final ReentrantLock lock;
    public ResourceManage() {
        this.resourceArray = new boolean[10];//��Ų���״̬
        this.semaphore = new Semaphore(10,true);//����10��������Դ��ʹ�ã�ʹ���Ƚ��ȳ��Ĺ�ƽģʽ���й���;��ƽģʽ���ź������������Ȼ���ź���
        this.lock = new ReentrantLock(true);//��ƽģʽ��������������ѡ
        for(int i=0 ;i<10; i++){
            resourceArray[i] = true;//��ʼ��Ϊ��Դ���õ����
        }
    }
    public void useResource(int userId){
        try{
            semaphore.acquire();
            int id = getResourceId();//ռ��һ����
            System.out.print("userId:"+userId+"����ʹ����Դ����Դid:"+id+"\n");
            Thread.sleep(100);//do something���൱����ʹ����Դ
            resourceArray[id] = true;//�˳������
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            semaphore.release();//�ͷ��ź�������������1
        }
    }
    private int getResourceId(){
        int id = -1;
        try {
            lock.lock();//��Ȼʹ����������ͬ����������ֻ�Ǽ򵥵�һ�����������Ч�ʻ��Ǻܸߵģ����Ի�����Ӱ�����ܡ�
            for(int i=0; i<10; i++){
                if(resourceArray[i]){
                    resourceArray[i] = false;
                    id = i;
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return id;
    }
}
public class SemaphoreTest implements Runnable{
    private ResourceManage resourceManage;
    private int userId;
    public SemaphoreTest(ResourceManage resourceManage, int userId) {
        this.resourceManage = resourceManage;
        this.userId = userId;
    }
    public void run(){
        System.out.print("userId:"+userId+"׼��ʹ����Դ...\n");
        resourceManage.useResource(userId);
        System.out.print("userId:"+userId+"ʹ����Դ���...\n");
    }

    public static void main(String[] args){
        ResourceManage resourceManage = new ResourceManage();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new SemaphoreTest(resourceManage,i));//���������Դʹ����
            threads[i] = thread;
        }
        for(int i = 0; i < 100; i++){
            Thread thread = threads[i];
            try {
                thread.start();//�����߳�
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}