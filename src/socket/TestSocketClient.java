package socket;

import java.io.*;
import java.net.*;

public class TestSocketClient implements Runnable{
	public static void main(String args[]) throws Exception {  
	      new Thread(new TestSocketClient()).start();
	      new Thread(new TestSocketClient()).start();
	      new Thread(new TestSocketClient()).start();
	}

	public void run() {
		 //Ϊ�˼���������е��쳣��ֱ��������  
	     String host = "127.0.0.1";  //Ҫ���ӵķ����IP��ַ  
	     int port = 2222;   //Ҫ���ӵķ���˶�Ӧ�ļ����˿�  
	     //�����˽�������  
	     Socket client;
		try {
			//�������Ӻ�Ϳ����������д������  
			client = new Socket(host, port);
			Writer writer = new OutputStreamWriter(client.getOutputStream());  
			writer.write("Hello Server.");  
			writer.write("eof");  
			writer.flush();  
			//д���Ժ���ж�����  
			Reader reader = new InputStreamReader(client.getInputStream());  
			char chars[] = new char[64];  
			int len;  
			StringBuffer sb = new StringBuffer();  
			String temp;  
			int index;  
			while ((len=reader.read(chars)) != -1) {  
				temp = new String(chars, 0, len);  
				if ((index = temp.indexOf("eof")) != -1) {  
					sb.append(temp.substring(0, index));  
					break;  
				}  
				sb.append(new String(chars, 0, len));  
			}  
			System.out.println("from server: " + sb);  
			writer.close();  
			reader.close();  
			client.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	
}
