package socket;

import java.io.*;
import java.net.*;

public class TestSocketClient implements Runnable{
	public static void main(String args[]) throws Exception {  
	      new Thread(new TestSocketClient()).start();
//	      new Thread(new TestSocketClient()).start();
//	      new Thread(new TestSocketClient()).start();
	}

	public void run() {
		 //Ϊ�˼���������е��쳣��ֱ��������  
	     String host = "115.159.37.123";  //Ҫ���ӵķ����IP��ַ  
	     int port = 58158;   //Ҫ���ӵķ���˶�Ӧ�ļ����˿�  
	     //�����˽�������  
	     Socket client;
		try {
			//�������Ӻ�Ϳ����������д������  
			client = new Socket(host, port);
//			Writer writer = new OutputStreamWriter(client.getOutputStream());  
//			writer.write("Hello Server.");  
//			writer.write("eof");  
//			writer.flush();  
			DataOutputStream dout = new DataOutputStream(client.getOutputStream());
			File file = new File("D:\\download\\dota2mods-cn.zip");
			dout.writeUTF(file.getName());
            FileInputStream fin = new FileInputStream(file);
            byte[] sendByte = new byte[10240000];
            int length = 0;

//            while((length = fin.read(sendByte, 0, sendByte.length))>0){
                dout.write(sendByte,0,length);
                dout.flush();
//            }
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
//			writer.close();  
			dout.close();  fin.close();
			reader.close();  
			client.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	
}
