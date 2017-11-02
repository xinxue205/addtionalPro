package server.client;

import java.io.*;
import java.net.*;

public class SocketClientTest {
	public static void main(String[] args) throws Exception {
		boolean lenthCon = true;
		String sendMsg = "<name>肖恩</name><content>hello server</content>";
		SocketAddress sa = new InetSocketAddress("127.0.0.1", 13330);
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;

		if (lenthCon){
			int length = sendMsg.length();
			sendMsg = length>999?length+sendMsg: length>99?"0"+length+sendMsg: length>9?"00"+length+sendMsg: "000"+length+sendMsg;
		}
		try {
			socket = new Socket();
			socket.connect(sa, 30*1000);// 连接超时时间
			socket.setSoTimeout(30*1000);// 读取信息超时时间
	
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
			pw.print(sendMsg);
			pw.flush();
			String returnMsg = br.readLine();
			System.out.println(returnMsg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (pw != null) {
					pw.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
