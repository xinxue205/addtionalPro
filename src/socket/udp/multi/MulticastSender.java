package socket.udp.multi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender {

private static int port = 8000;

private static String address = "228.0.0.4";//�鲥��ַ�ķ�Χ��224.0.0.0--- 239.255.255.255֮�䣨��ΪD���ַ 1110��ͷ��

	public static void main(String[] args) throws Exception {
		MulticastSocket mss = new MulticastSocket(port);
	
		try {
		
			InetAddress group = InetAddress.getByName(address); 
			
			mss.joinGroup(group);
			
			while (true) {
			
				String message = "Hello from node1";
				
				byte[] buffer = message.getBytes(); 
				
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length, group, port);//��Ҫָ��address��port
				
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
