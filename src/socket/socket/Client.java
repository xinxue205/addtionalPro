/**
 * 
 */
package socket.socket;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/**
 * @author wuxinxue
 * @time 2015-7-6 ����10:48:43
 * @copyright hnisi
 */
public class Client {  
	  
	public static void main(String args[]) throws Exception {  
	      //Ϊ�˼���������е��쳣��ֱ��������  
	      String host = "127.0.0.1";  //Ҫ���ӵķ����IP��ַ  
	      int port = 8899;   //Ҫ���ӵķ���˶�Ӧ�ļ����˿�  
	      //�����˽�������  
	      Socket client = new Socket(host, port);  
	      //�������Ӻ�Ϳ����������д������  
	      Writer writer = new OutputStreamWriter(client.getOutputStream());  
	      writer.write("Hello Server.");  
	      writer.flush();//д���Ҫ�ǵ�flush  
	      writer.close();  
	      client.close();  
	   }
}
