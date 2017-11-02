
package log;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author popular
 *
 */
public class SystemOutTest {

	/**
	 * 描述：设置SYSTEM.OUT的内容到指定文件
	 * @param args
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	public static void main(String[] args) {
		String s ="44444 /n  444444";
		PrintStream ps = null;
		try {
			//输出内容到SystemOut.log文件
			ps = new PrintStream("SystemOut.log");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setOut(ps);
		System.out.println(s);
	}
	
	static int mount(int a){
		int s;
		if (a>0){
			s=1;
		} else if (a<0){
			s=-1;
		} else s=0;
		return s;
	}
}
