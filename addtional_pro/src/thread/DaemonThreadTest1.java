package thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * ���ܰ���������߼���װ���ػ��߳�
 * @author wuxinxue
 * @copyRight sinobest
 * @time 2016-11-18 ����11:55:51
 */
public class DaemonThreadTest1{     
    public static void main(String[] args) throws InterruptedException     
    {     
        Runnable tr=new TestRunnable();     
        Thread thread=new Thread(tr);     
                thread.setDaemon(true); //�����ػ��߳�     
        thread.start(); //��ʼִ�зֽ���     
    }     
}     
//���н�����ļ�daemon.txt��û��"daemon"�ַ�����  

class TestRunnable implements Runnable{     
    public void run(){     
               try{     
                  Thread.sleep(1000);//�ػ��߳�����1�������     
                  File f=new File("daemon.txt");     
                  FileOutputStream os=new FileOutputStream(f,true);     
                  os.write("daemon".getBytes());     
           }     
               catch(IOException e1){     
          e1.printStackTrace();     
               }     
               catch(InterruptedException e2){     
                  e2.printStackTrace();     
           }     
    }     
} 