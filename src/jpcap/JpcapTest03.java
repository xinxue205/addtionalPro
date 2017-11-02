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
	 * 组织ICMP报文发送，并开启线程接收报文
	 * @param ip  
	 */ 
	public void ping(String ip) {  
		try {   
			jpcap = JpcapCaptor.openDevice(devices[0], 200, false, 20);
			sender = jpcap.getJpcapSenderInstance();
			jpcap.setFilter("icmp", true); // 过滤器，只接受ICMP报文   
			icmpPacket = new ICMPPacket();
			icmpPacket.type = ICMPPacket.ICMP_ECHO;  // 发送回显请求报文   
			/*for (int i=0;i<devices.length;i++){
				System.out.println("网卡："+devices[i].name);
				NetworkInterfaceAddress[] addresses = devices[i].addresses;
				for (int j = 0; j < addresses.length; j++) {
					System.out.println("地址："+addresses[j].address);
				}
			}*/
			icmpPacket.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_ICMP, devices[1].addresses[4].address, InetAddress.getByName(ip));
			
			icmpPacket.data = "abcdefghijklmnopqrstuvwxyzabcdef".getBytes();   // 随意的32bytes数据   
			EthernetPacket ethernetPacket = new EthernetPacket();   
			ethernetPacket.frametype = EthernetPacket.ETHERTYPE_IP;   
			ethernetPacket.src_mac = devices[0].mac_address;   // 广播地址   
			ethernetPacket.dst_mac = new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };
			icmpPacket.datalink = ethernetPacket;
			listResult.add("Pinging " + icmpPacket.dst_ip + " with " + icmpPacket.data.length + " bytes of data: ");
			startCapThread(jpcap);
			for (int i = 0; i < 5; i++) {    
				icmpPacket.sec = 0;
				//icmpPacket.usec = System.currentTimeMillis();
				icmpPacket.usec = new GregorianCalendar().getTimeInMillis();
				// 记录发送时间    icmpPacket.seq = (short) (1000 + i);
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
	 * 接收ICMP报文  
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
					// 若是ICMP回应报文，则列出。。。    
					tmp = (rp.sec * 1000 + rp.usec / 1000 - icmpPacket.sec * 1000 - icmpPacket.usec);
					// 计算发送与接受的时间差     
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
	 * 接收ICMP报文 
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

