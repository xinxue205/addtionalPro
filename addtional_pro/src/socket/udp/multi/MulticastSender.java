package socket.udp.multi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender {

private static int port = 8000;

private static String address = "228.0.0.4";//组播地址的范围在224.0.0.0--- 239.255.255.255之间（都为D类地址 1110开头）

	public static void main(String[] args) throws Exception {
		MulticastSocket mss = new MulticastSocket(port);
	
		try {
		
			InetAddress group = InetAddress.getByName(address); 
			
			mss.joinGroup(group);
			
			while (true) {
			
				String message = "Hello from node1";
				
				byte[] buffer = message.getBytes(); 
				
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length, group, port);//需要指定address和port
				
				mss.send(dp); 
				System.out.println("send to cast 228.0.0.4: "+message);
				
				Thread.sleep(1000);
		
			}
		
		} catch (IOException e) {
		
			e.printStackTrace();
		
		} finally {
			mss.close();
		}
	}

}
