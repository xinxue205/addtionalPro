
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
	 * ����������SYSTEM.OUT�����ݵ�ָ���ļ�
	 * @param args
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	public static void main(String[] args) {
		String s ="44444 /n  444444";
		PrintStream ps = null;
		try {
			//������ݵ�SystemOut.log�ļ�
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
