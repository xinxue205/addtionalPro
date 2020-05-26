package thread;

/**
 * Java 中有一块主内存，不同的线程有自己的工作内存，同一个变量值在主内存中有一份，如果线程用到了这个变量的话，自己的工作内存中有一份一模一样的拷贝。每次进入线程从主内存中拿到变量值，每次执行完线程将变量从工作内存同步回主内存中。<br>
 * 当不同线程的工作内存不同步时，就会出现值不一致，这就需要volatile修饰； volatile的意思是，子线程读取 参数值时，都先从主内存中把 isRunning 同步到线程的工作内存
 * @author Administrator
 *
 */
public class TestVolatile {
	public static void main(String[] args) {
		try
	    {
			TestVolatile1 mt = new TestVolatile1();
	        mt.start();
	        Thread.sleep(1000);
	        mt.setRunning(false);
	        System.out.println("已赋值为false");
	    }
	    catch (InterruptedException e)
	    {
	        e.printStackTrace();
	    }
	}
}

class TestVolatile1 extends Thread
{
    private boolean isRunning = true;

    public boolean isRunning()
    {
        return isRunning;
    }

    public void setRunning(boolean isRunning)
    {
        this.isRunning = isRunning;
    }
    
    public void run()
    {
        System.out.println("进入run了");
        while (isRunning == true){
        	//**************下面这一行很有意思，打开就会同步isRunning值
        	//System.out.println("子线程isRunning："+isRunning);
        }
        System.out.println("线程被停止了");
    }
}
