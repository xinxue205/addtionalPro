package jpcap;
import jpcap.packet.*;

public class JpcapTest02{
	@SuppressWarnings("restriction")
	public static void main(String [] args) throws Exception{
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 0;
		if(devices.length>1) index = 1; /*ȥ�����������Ĵ���*/
		JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535, false, 1);
		captor.setFilter("ip",true);
		captor.loopPacket(5, new Receiver());
		}
	}

	@SuppressWarnings("restriction")
	class Receiver implements PacketReceiver{  /*ʵ��receivePacket����*/
		public void receivePacket(Packet packet){
			if(true) System.out.println("="+packet.toString()+"===");
			System.out.println("a2425".matches("2425"));
			/*System.out.println("===================================");
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
			System.out.println("==================================="); */
		}
	}