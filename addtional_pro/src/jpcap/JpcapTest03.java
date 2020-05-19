package jpcap;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;

@SuppressWarnings("restriction")
public class JpcapTest03 {
	private NetworkInterface[] devices = JpcapCaptor.getDeviceList();
	private JpcapSender sender;
	private JpcapCaptor jpcap;
	private ICMPPacket icmpPacket;
	private List<String> listResult = new ArrayList<String>(); 
	
	/**  
	 * ��֯ICMP���ķ��ͣ��������߳̽��ձ���
	 * @param ip  
	 */ 
	public void ping(String ip) {  
		try {   
			jpcap = JpcapCaptor.openDevice(devices[0], 200, false, 20);
			sender = jpcap.getJpcapSenderInstance();
			jpcap.setFilter("icmp", true); // ��������ֻ����ICMP����   
			icmpPacket = new ICMPPacket();
			icmpPacket.type = ICMPPacket.ICMP_ECHO;  // ���ͻ���������   
			/*for (int i=0;i<devices.length;i++){
				System.out.println("������"+devices[i].name);
				NetworkInterfaceAddress[] addresses = devices[i].addresses;
				for (int j = 0; j < addresses.length; j++) {
					System.out.println("��ַ��"+addresses[j].address);
				}
			}*/
			icmpPacket.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_ICMP, devices[1].addresses[4].address, InetAddress.getByName(ip));
			
			icmpPacket.data = "abcdefghijklmnopqrstuvwxyzabcdef".getBytes();   // �����32bytes����   
			EthernetPacket ethernetPacket = new EthernetPacket();   
			ethernetPacket.frametype = EthernetPacket.ETHERTYPE_IP;   
			ethernetPacket.src_mac = devices[0].mac_address;   // �㲥��ַ   
			ethernetPacket.dst_mac = new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };
			icmpPacket.datalink = ethernetPacket;
			listResult.add("Pinging " + icmpPacket.dst_ip + " with " + icmpPacket.data.length + " bytes of data: ");
			startCapThread(jpcap);
			for (int i = 0; i < 5; i++) {    
				icmpPacket.sec = 0;
				//icmpPacket.usec = System.currentTimeMillis();
				icmpPacket.usec = new GregorianCalendar().getTimeInMillis();
				// ��¼����ʱ��    icmpPacket.seq = (short) (1000 + i);
				icmpPacket.id = (short) (999 + i);
				sender.sendPacket(icmpPacket);
				try {     
					Thread.sleep(1000);    
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();  
		} 
	} 
	
	/**  
	 * ����ICMP����  
	 @param jpcap  
	 */ 
	public void getIcmpPacket(JpcapCaptor jpcapCaptor) {
		try {
			while (true) {
				long tmp = 0;
				String tmpStr = null;
				ICMPPacket rp;
				rp = (ICMPPacket) jpcapCaptor.getPacket();
				if ((rp != null) && (rp.seq - rp.id == 1)&& (rp.type == ICMPPacket.ICMP_ECHOREPLY)) {
					// ����ICMP��Ӧ���ģ����г�������    
					tmp = (rp.sec * 1000 + rp.usec / 1000 - icmpPacket.sec * 1000 - icmpPacket.usec);
					// ���㷢������ܵ�ʱ���     
					if (tmp <= 0)      tmpStr = " < 1 ms ";
					else      tmpStr = "= " + tmp + " ms  ";
					System.out.println("Reply from "+ rp.src_ip.getHostAddress() + ": bytes = "+ rp.data.length + " time " + tmpStr + "TTL = "       + rp.hop_limit);
					listResult.add("Reply from " + rp.src_ip.getHostAddress()       + ": bytes = " + rp.data.length + " time " + tmpStr       + "TTL = " + rp.hop_limit);
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();  
		} 
	} 
	
	/**
	 * ����ICMP���� 
	 @param jpcap  
	 */ 
	public void startCapThread(final JpcapCaptor jpcap) {
		Runnable runner = new Runnable() {
			public void run() {
				getIcmpPacket(jpcap);
			}
		};  
		new Thread(runner).start(); 
	} 

	public static void main(String[] args) {
		new JpcapTest03().ping("10.2.30.187"); 
	}
}

