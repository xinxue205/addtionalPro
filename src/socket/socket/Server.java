/**
 * 
 */
package socket.socket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

	public static void main(String args[]) throws IOException {  
	      //Ϊ�˼���������е��쳣��Ϣ��������  
	      int port = 8899;  
	      //����һ��ServerSocket�����ڶ˿�8899��  
	      ServerSocket server = new ServerSocket(port);  
	      //server���Խ�������Socket����������server��accept����������ʽ��  
	      Socket socket = server.accept();  
	      //���ͻ��˽���������֮�����ǾͿ��Ի�ȡsocket��InputStream�������ж�ȡ�ͻ��˷���������Ϣ�ˡ�  
	      Reader reader = new InputStreamReader(socket.getInputStream());  
	      char chars[] = new char[64];  
	      int len;  
	      StringBuilder sb = new StringBuilder();  
	      while ((len=reader.read(chars)) != -1) {  
	         sb.append(new String(chars, 0, len));  
	      }  
	      System.out.println("from client: " + sb);  
	      reader.close();  
	      socket.close();  
	      server.close();  
	   }
}
