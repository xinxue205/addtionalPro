package socket.udp.multi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver {
	private static int port = 8000;

	private static String address = "228.0.0.4";//�鲥��ַ�ķ�Χ��224.0.0.0--- 239.255.255.255֮�䣨��ΪD���ַ 1110��ͷ��

	public static void main(String[] args) throws Exception {

		InetAddress group = InetAddress.getByName(address); 
	
		MulticastSocket msr = null;
	
		try {
		
			msr = new MulticastSocket(port);
		
			msr.joinGroup(group);
		
			byte[] buffer = new byte[1024];
		
			while (true) {
		
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length); 
			
				msr.receive(dp); 
			
				String s = new String(dp.getData(), 0, dp.getLength()); 
			
				System.out.println("receive from node1:"+s);
		
			}
	
		} catch (IOException e) {
	
			e.printStackTrace();
	
		} 

	}
}
