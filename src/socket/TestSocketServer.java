package socket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSocketServer{
	public static void main(String args[]) throws IOException, Exception {  
	      //Ϊ�˼���������е��쳣��Ϣ��������  
	     int port = 8899;  
	      //����һ��ServerSocket�����ڶ˿�8899��  
	     ServerSocket server = new ServerSocket(port);  
	      while (true) {  
	         //server���Խ�������Socket����������server��accept����������ʽ��  
	       Socket socket = server.accept();  
	         //���ͻ��˽���������֮�����ǾͿ��Ի�ȡsocket��InputStream�������ж�ȡ�ͻ��˷���������Ϣ�ˡ�  
	       Reader reader = new InputStreamReader(socket.getInputStream());  
	         char chars[] = new char[64];  
	         int len;  
	         StringBuilder sb = new StringBuilder();  
	         String temp;  
	         int index;  
	         while ((len=reader.read(chars)) != -1) {  
	            temp = new String(chars, 0, len);  
	            if ((index = temp.indexOf("eof")) != -1) {//����eofʱ�ͽ�������  
	                sb.append(temp.substring(0, index));  
	                break;  
	            }  
	            sb.append(temp);  
	         }  
	         System.out.println("from client: " + sb);  
	         Thread.sleep(10000);
	         //�����дһ��  
	       Writer writer = new OutputStreamWriter(socket.getOutputStream());  
	         writer.write("Hello Client.");  
	         writer.flush();  
	         writer.close();  
	         reader.close();  
	         socket.close();  
	      }  
	   }
}
