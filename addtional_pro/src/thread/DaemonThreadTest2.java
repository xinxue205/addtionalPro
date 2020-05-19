package thread;

import java.io.*;

class TestRunnable1 implements Runnable{

    public void run(){
       try{
          Thread.sleep(1000);//守护线程阻塞1秒后运行
          File f=new File("daemon.txt");
          FileOutputStream os=new FileOutputStream(f,true);
          os.write("daemon".getBytes());
       } catch(IOException e1){
    	   e1.printStackTrace();
       } catch (InterruptedException e2){
    	   e2.printStackTrace();
       }
    }
}

public class DaemonThreadTest2{
    public static void main(String[] args) {
        Runnable tr=new TestRunnable1();
        Thread thread=new Thread(tr);
//        thread.setDaemon(true); //设置守护线程 
        thread.start(); //开始执行分进程 
    }
}
