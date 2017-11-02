package thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 不能把输入输出逻辑包装进守护线程
 * @author wuxinxue
 * @copyRight sinobest
 * @time 2016-11-18 上午11:55:51
 */
public class DaemonThreadTest1{     
    public static void main(String[] args) throws InterruptedException     
    {     
        Runnable tr=new TestRunnable();     
        Thread thread=new Thread(tr);     
                thread.setDaemon(true); //设置守护线程     
        thread.start(); //开始执行分进程     
    }     
}     
//运行结果：文件daemon.txt中没有"daemon"字符串。  

class TestRunnable implements Runnable{     
    public void run(){     
               try{     
                  Thread.sleep(1000);//守护线程阻塞1秒后运行     
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