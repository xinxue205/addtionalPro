package jpcap;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.*;

@SuppressWarnings("restriction")
public class JpcapTest01 implements Runnable,KeyListener {
	static JpcapCaptor captor;
	static NetworkInterface[] devices;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int i = 0;
	static String str;
	static IPPacket p;
	static IPPacket ip;
	static boolean bl = true;
	public static void main(String[] args) {
		System.out.println("ARP Poisoning Tool by N3trino. JARP v 0.3");
		nic();
		try {
			captor = JpcapCaptor.openDevice(devices[i], 65535, true, 20);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
		Runnable runnable = new JpcapTest01();
		Thread thread = new Thread(runnable);
		thread.setName("JARP");
		thread.setPriority(8);
		thread.start();
	}

	public void run() {
		while (bl) {
			captor.processPacket(-1, handler);
		}
	}
	
	public static int nic() {
		devices = JpcapCaptor.getDeviceList();
		for (int i = 0; i < devices.length; i++) {
			System.out.println(i + ": " + devices[i].name + "("+ devices[i].description + ")");
			for (NetworkInterfaceAddress a : devices[i].addresses) {
				System.out.println(" address:" + a.address);
			}
		}
		System.out.print("> Choose the NIC you want to use: ");
		try {
			str = in.readLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		i = Integer.parseInt(str);
		return i;
	}
	
	private static PacketReceiver handler = new PacketReceiver() {  
		public void receivePacket(Packet packet) {
			System.out.println("===================================");
			System.out.println("�汾��:        " + ((IPPacket)packet).version+"");
			System.out.println("�ײ�����:      " + packet.header.length +"");
			System.out.println("�ܳ���:        " + ((IPPacket)packet).len + "");
			System.out.println("��ʾ�ֶ�:      " + (((IPPacket)packet).dont_frag == true ? "�ֶ�" : "���ֶ�"));
			System.out.println("��־�ֶ�:      " + ((IPPacket)packet).ident + "");
			System.out.println("ƫ����:        " + ((IPPacket)packet).offset +"");
			System.out.println("TTL�ֶ�:        " + ((IPPacket)packet).hop_limit+"");
			System.out.println("Э������:      " + ((IPPacket)packet).protocol);
			System.out.println("У���:        " + "");
			System.out.println("ԴIP��ַ:      " + ((IPPacket)packet).src_ip.toString());
			System.out.println("Ŀ��IP��ַ:    " + ((IPPacket)packet).dst_ip.toString());
			System.out.println("===================================");  
		}
	};

	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){  
		if(e.getModifiers()==InputEvent.CTRL_MASK&&e.getKeyCode() == KeyEvent.VK_X){
			System.out.println("fjdlajf");
			System.exit(1);
			stopCapture();
			System.out.println("Stop capture!");  
		}
	}
	void stopCapture(){
		bl = false;
	}
}