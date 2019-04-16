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
		 //为了简单起见，所有的异常都直接往外抛  
	     String host = "115.159.37.123";  //要连接的服务端IP地址  
	     int port = 58158;   //要连接的服务端对应的监听端口  
	     //与服务端建立连接  
	     Socket client;
		try {
			//建立连接后就可以往服务端写数据了  
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
			//写完以后进行读操作  
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
