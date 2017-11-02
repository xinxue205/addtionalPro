/**
 * 
 */
package thread;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-10-13 上午11:13:06
 * @Description
 * @version 1.0 Shawn create
 */
public class MultiThreadTest2_2 {
	public static void main(String[] args) throws Exception {
		cal(1);
	}
	
	public static int cal(int stepV) throws Exception{
		int value =  stepV ;
		Thread.sleep(1000);
		System.out.println(value);
		value = value+stepV; 
		Thread.sleep(1000);
		System.out.println(value);
		value = value+stepV; 
		Thread.sleep(1000);
		System.out.println(value);
		value = value+stepV; 
		Thread.sleep(1000);
		System.out.println(value);
		value = value+stepV; 
		Thread.sleep(1000);
		System.out.println(value);
		value = value+stepV; 
		Thread.sleep(1000);
		System.out.println(value);
		value = value+stepV; 
		Thread.sleep(1000);
		System.out.println(value);
		value = value+stepV; 
		Thread.sleep(1000);
		System.out.println(value);
		value = value+stepV; 
		Thread.sleep(1000);
		System.out.println(value);
		value = value+stepV; 
		Thread.sleep(1000);
		System.out.println(value);
		return value;
	}
}
