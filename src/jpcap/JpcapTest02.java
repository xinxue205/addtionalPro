package jpcap;
import jpcap.packet.*;

public class JpcapTest02{
	@SuppressWarnings("restriction")
	public static void main(String [] args) throws Exception{
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 0;
		if(devices.length>1) index = 1; /*去掉虚拟网卡的处理*/
		JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535, false, 1);
		captor.setFilter("ip",true);
		captor.loopPacket(5, new Receiver());
		}
	}

	@SuppressWarnings("restriction")
	class Receiver implements PacketReceiver{  /*实例receivePacket方法*/
		public void receivePacket(Packet packet){
			if(true) System.out.println("="+packet.toString()+"===");
			System.out.println("a2425".matches("2425"));
			/*System.out.println("===================================");
			System.out.println("版本号:        " + ((IPPacket)packet).version+"");
			System.out.println("首部长度:      " + packet.header.length +"");
			System.out.println("总长度:        " + ((IPPacket)packet).len + "");
			System.out.println("标示字段:      " + (((IPPacket)packet).dont_frag == true ? "分段" : "不分段"));
			System.out.println("标志字段:      " + ((IPPacket)packet).ident + "");
			System.out.println("偏移量:        " + ((IPPacket)packet).offset +"");
			System.out.println("TTL字段:        " + ((IPPacket)packet).hop_limit+"");
			System.out.println("协议类型:      " + ((IPPacket)packet).protocol);
			System.out.println("校验和:        " + "");
			System.out.println("源IP地址:      " + ((IPPacket)packet).src_ip.toString());
			System.out.println("目的IP地址:    " + ((IPPacket)packet).dst_ip.toString());
			System.out.println("==================================="); */
		}
	}