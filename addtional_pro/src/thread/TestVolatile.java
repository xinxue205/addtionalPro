package thread;

/**
 * Java ����һ�����ڴ棬��ͬ���߳����Լ��Ĺ����ڴ棬ͬһ������ֵ�����ڴ�����һ�ݣ�����߳��õ�����������Ļ����Լ��Ĺ����ڴ�����һ��һģһ���Ŀ�����ÿ�ν����̴߳����ڴ����õ�����ֵ��ÿ��ִ�����߳̽������ӹ����ڴ�ͬ�������ڴ��С�<br>
 * ����ͬ�̵߳Ĺ����ڴ治ͬ��ʱ���ͻ����ֵ��һ�£������Ҫvolatile���Σ� volatile����˼�ǣ����̶߳�ȡ ����ֵʱ�����ȴ����ڴ��а� isRunning ͬ�����̵߳Ĺ����ڴ�
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
	        System.out.println("�Ѹ�ֵΪfalse");
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
        System.out.println("����run��");
        while (isRunning == true){
        	//**************������һ�к�����˼���򿪾ͻ�ͬ��isRunningֵ
        	//System.out.println("���߳�isRunning��"+isRunning);
        }
        System.out.println("�̱߳�ֹͣ��");
    }
}
