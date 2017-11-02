/**
 * 
 */
package system;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author wuxinxue
 * @time 2015-6-17 ÏÂÎç5:17:11
 * @copyright hnisi
 */
public class TestRunTime {
	public static void main(String[] args) throws IOException {
		System.out.println(Runtime.getRuntime().totalMemory());
		System.out.println(Runtime.getRuntime().maxMemory());
		System.out.println(Runtime.getRuntime().freeMemory());
//		String argus[] = {"notepad.exe","d:/alarm.txt"};
//		Runtime.getRuntime().exec(argus);
		System.out.println("line.separator:"+System.getProperty("line.separator"));
		System.out.println("path.separator:"+System.getProperty("path.separator"));
		System.out.println("file.separator:"+System.getProperty("file.separator"));
	}	
}
