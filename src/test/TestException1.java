package test;

import java.io.IOException;

public class TestException1 {
	public static void main(String args[]) {
		ExceptionTest et = new ExceptionTest("错误:不能为负数");
		int num1 = 55;
		int[] arr1 = {1,-1,5,6};
		for (int i=0;i<arr1.length;i++){
			if (arr1[i]==-1) {
				et.printStackTrace();
				//break;
				return;
			} else {
				System.out.println(num1/arr1[i]);
			}
		}
	}
} 
class ExceptionTest extends Exception{
	public ExceptionTest(String message ){
		   super( message );
	}
}