package test;

/**
 *
 * @Author zhangchunhua.co
 * @Date 2013-11-22 ����3:42:06
 * @Version 1.0  zhangchunhua.co create
 * @CopyRight (c) 2013 �����������ϵͳ���޹�˾ 
 * @Description 
 */
public class Test1 extends Thread {
	public static void main(String[] args) {
		try{
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setProperty("testParam", "222");
	}
}
